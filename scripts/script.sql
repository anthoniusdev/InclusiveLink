create database infinity_link;
use infinity_link;
create table pessoa(
	idPessoa       int auto_increment primary key,
    nome           varchar(256) null,
    dataNascimento date         null,
    email          varchar(45)  null,
    senha          varchar(255) null,
    dataCriacao    date         null,
    constraint email_unico
        unique (email)
);

create table membro(
    idPessoa    int auto_increment primary key,
    fotoPerfil  longtext     null,
    fotoFundo   longtext     null,
    nomeUsuario varchar(45)  null,
    descricao   varchar(256) null,
    dataCriacao date         null,
    constraint nomeUsuario_unico
        unique (nomeUsuario),
    constraint membro_ibfk_1
        foreign key (idPessoa) references pessoa (idPessoa)
            on delete cascade
);

create table comunidade
(
    idComunidade int auto_increment
        primary key,
    nome         varchar(45)  null,
    idCriador    int          null,
    fotoPerfil   mediumtext   null,
    fotoFundo    mediumtext   null,
    descricao    varchar(256) null,
    constraint comunidade_ibfk_1
        foreign key (idCriador) references membro (idPessoa)
);

create index idCriador
    on comunidade (idCriador);

create trigger trg_novoMembro
    before insert
    on membro
    for each row
    SET NEW.dataCriacao = CURRENT_TIMESTAMP;

create table membro_seguidor
(
    idMembro         int not null,
    idSeguidor       int not null,
    numeroSeguidores int null,
    primary key (idMembro, idSeguidor),
    constraint membro_seguidor_ibfk_1
        foreign key (idMembro) references membro (idPessoa)
            on delete cascade,
    constraint membro_seguidor_ibfk_2
        foreign key (idSeguidor) references membro (idPessoa)
            on delete cascade
);

create index idSeguidor
    on membro_seguidor (idSeguidor);

create table membro_seguindo
(
    idMembro        int not null,
    idSeguindo      int not null,
    numeroSeguindos int null,
    primary key (idMembro, idSeguindo),
    constraint membro_seguindo_ibfk_1
        foreign key (idMembro) references membro (idPessoa)
            on delete cascade,
    constraint membro_seguindo_ibfk_2
        foreign key (idSeguindo) references membro (idPessoa)
            on delete cascade
);

create index idSeguindo
    on membro_seguindo (idSeguindo);

create table participante_comunidade
(
    idParticipante int not null,
    idComunidade   int not null,
    primary key (idParticipante, idComunidade),
    constraint participante_comunidade_ibfk_1
        foreign key (idParticipante) references membro (idPessoa)
            on delete cascade,
    constraint participante_comunidade_ibfk_2
        foreign key (idComunidade) references comunidade (idComunidade)
            on delete cascade
);

create table moderador_comunidade
(
    idModerador  int not null,
    idComunidade int not null,
    primary key (idModerador, idComunidade),
    constraint moderador_comunidade_ibfk_1
        foreign key (idModerador) references participante_comunidade (idParticipante)
            on delete cascade,
    constraint moderador_comunidade_ibfk_2
        foreign key (idComunidade) references comunidade (idComunidade)
            on delete cascade
);

create index idComunidade
    on moderador_comunidade (idComunidade);

create index idComunidade
    on participante_comunidade (idComunidade);

create trigger trg_novaPessoa
    before insert
    on pessoa
    for each row
    SET NEW.dataCriacao = CURRENT_TIMESTAMP;

create table publicacao
(
    idPublicacao int auto_increment,
    texto        varchar(256) null,
    midia        longtext     null,
    id_autor     int          not null,
    data         date         null,
    hora         time         null,
    primary key (idPublicacao, id_autor),
    constraint publicacao_ibfk_1
        foreign key (id_autor) references membro (idPessoa)
            on delete cascade
);

create index id_autor
    on publicacao (id_autor);

create trigger trg_novaPublicacao
    before insert
    on publicacao
    for each row
    SET NEW.data = CURDATE(), NEW.hora = CURTIME();

create table publicacao_comentario
(
    idComentario int auto_increment,
    idPublicacao int          not null,
    texto        varchar(256) null,
    midia        longtext     null,
    id_autor     int          not null,
    data         date         null,
    hora         time         null,
    primary key (idComentario, idPublicacao, id_autor),
    constraint publicacao_comentario_ibfk_1
        foreign key (idPublicacao) references publicacao (idPublicacao)
            on delete cascade,
    constraint publicacao_comentario_ibfk_2
        foreign key (id_autor) references membro (idPessoa)
            on delete cascade
);

create table comentario_curtida
(
    idComentario int not null,
    idMembro     int not null,
    primary key (idComentario, idMembro),
    constraint comentario_curtida_ibfk_1
        foreign key (idComentario) references publicacao_comentario (idComentario)
            on delete cascade,
    constraint comentario_curtida_ibfk_2
        foreign key (idMembro) references membro (idPessoa)
            on delete cascade
);

create index idMembro
    on comentario_curtida (idMembro);

create index idPublicacao
    on publicacao_comentario (idPublicacao);

create index id_autor
    on publicacao_comentario (id_autor);

create trigger rg_novoComentario
    before insert
    on publicacao_comentario
    for each row
    set new.data = curdate(), new.hora = curtime();

create table publicacao_comunidade
(
    idPublicacao int not null,
    idComunidade int not null,
    primary key (idPublicacao, idComunidade),
    constraint publicacao_comunidade_ibfk_1
        foreign key (idPublicacao) references publicacao (idPublicacao)
            on delete cascade,
    constraint publicacao_comunidade_ibfk_2
        foreign key (idComunidade) references comunidade (idComunidade)
            on delete cascade
);

create index idComunidade on publicacao_comunidade (idComunidade);

create table publicacao_curtida
(
    idPublicacao int not null,
    idMembro     int not null,
    primary key (idPublicacao, idMembro),
    constraint publicacao_curtida_ibfk_1
        foreign key (idPublicacao) references publicacao (idPublicacao)
            on delete cascade,
    constraint publicacao_curtida_ibfk_2
        foreign key (idMembro) references membro (idPessoa)
            on delete cascade
);

create index idMembro
    on publicacao_curtida (idMembro);

