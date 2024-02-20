import numpy as np
import cv2
from matplotlib import pyplot as plt

def showImagem(img):
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    plt.imshow(img)
    plt.show()
    
def main():
    imagem = cv2.imread('./Projeto - Fungos/Fotos/f0.png')
    altura, largura, canais_de_cor = imagem.shape
    print(f'Altura: {altura}\nLargura: {largura}\nCanais de cor: {canais_de_cor}')
    
    
    for y in range(0, altura):
        for x in range(0, largura):
            # if imagem[y][x] == 
             print(f'[{x},{y}] = {imagem[y][x]}')
            # azul = imagem.item(y, x, 0)            
            # verde = imagem.item(y, x, 1)            
            # vermelho = imagem.item(y, x, 2)
            
            # imagem.itemset
            
            # print(f'')
    
    # showImagem(imagem)
main()