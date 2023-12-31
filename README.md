# Batalha Naval

## Introdução 

Neste repositório está contida a implementação do jogo batalha naval em Java, onde foi utilizado o javaFX para construção da interface gráfica.

- [Pré-requisitos](#pré-requisitos) - Requisitos para executar o projeto em localmente.
- [Como executar](#como-executar) - Instruções sobre como executar o projeto no Eclipse IDE ou a partir do .jar.
- [Observações](#observacões) - Instruções sobre como executar o projeto no Eclipse IDE ou a partir do .jar.


## Pré-requisitos

Faz-se necessário os seguintes programas para executar o projeto com suas respectivas versões recomendadas:

| Nome: | Versão:  |    
| :---------- | :------------- |
|`JDK` 	| 17.0.7 |
|`Eclipse IDE Java`| 2022-09 |


## Como executar

Em seu computador execute os seguintes comandos para clonagem do repositório localmente em uma pasta de sua preferência:

``` bash
$ git clone https://github.com/L0ureiro/BatalhaNavalLPII.git
```

Para jogar basta abrir o arquivo `BatalhaNaval.jar`.

Caso deseje ver o código, abra-o no Eclipse IDE. Para isso será necessário instalar e configurar o `JavaFX 17.0.7` e suas dependências.

## Demonstração
 - Posicionamento

    <img src="assets\gif\PosNavio1.gif" width="300" >
    <img src="assets\gif\PosNavio2.gif" width="300" >

- Rotação dos navios

    <img src="assets\gif\RotaNavio1.gif" width="300" >
    <img src="assets\gif\RotaNavio2-1.gif" width="300" >

- Inicio de jogo e disparos

    <img src="assets\gif\Disparos1.gif" width="300" >
    <img src="assets\gif\Disparos2.gif" width="300" >

- Fim de jogo e reinicio 

    <img src="assets\gif\FimDeJogo.gif" width="300" > 
    <img src="assets\gif\JogarNovamente.gif" width="300" >   

## Observações

Não conseguimos gerar o arquivo da documentação (JavaDoc) e nem o .jar através do Eclipse, devido a isso, recriamos o projeto 
utilizando o IntelliJ IDEA. Nessa IDE foram criandas dependências Maven que possibilitaram a criação dos arquivos citados anteriormente.
Caso tente importar o .jar para o Eclipse você pode se deparar com alguns erros e também verá uma estrutura de pacotes e dependências 
diferentes da apresentada nesse repositório, devido ao fato de termos usado outra IDE para gerar o .jar.
