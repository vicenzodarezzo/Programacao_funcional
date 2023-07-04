
import Data.List (intercalate)

-- FUNÇÃO de CONTAGEM:
contarPontos :: (Eq t, Eq a, Num t, Num a) => [a] -> a -> t -> a

---- Caso tenha contado todos os pontos da lista, encerra contagem.
contarPontos [] pontos _ = pontos
---- Caso já esteja no 10º Flake (Última rodada), encerra contagem de pontos.
contarPontos lista pontos 10 = pontos

----  Caso genérico:
contarPontos lista pontos rodadas
    ---- Strike, pontos = 10 + 1ºpróximo lançamento + 2ºpróximo lançamento;
   | head lista == 10 = contarPontos (drop 1 lista) (pontos + sum (take 3 lista)) (rodadas+1)

    ---- Spare, pontos = 10 + 1ºpróximo lançamento;
   | sum (take 2 lista) == 10 = contarPontos (drop 2 lista) (pontos + sum (take 3 lista)) (rodadas+1)

    ---- Padrão, pontos = 1ºlançamento + 2ºlançamento;
   | otherwise = contarPontos (drop 2 lista) (pontos + sum (take 2 lista)) (rodadas+1)


-- FUNÇÃO DE AFERIMENTO DO TEXTO RELATIVO AOS FLAKES
---- utiliza como auxiliar a função gerarTripla para representar o Flake triplo, caso ele esteja incluso.

gerarTextoBoliche :: [Int] -> [String]

---- Caso base: lista vazia.
gerarTextoBoliche [] = []
---- Caso trivial : lista unitária.
gerarTextoBoliche [numeroUnico] = [show numeroUnico]
---- Caso genértico:
gerarTextoBoliche (first : second : tail)
    ---- Identificação do caso Flake triplo; 
    | length tail == 1 = gerarTripla (first : second : tail) ++ gerarTextoBoliche []
    ---- Strike;
    | first == 10 = "X"  : "_" : gerarTextoBoliche (second : tail)
    ---- Spare;
    | sum [first,second] == 10 = show first  :  "/"  : gerarTextoBoliche tail
    ---- Padrão;
    | otherwise = show first : show second : gerarTextoBoliche tail


-- FUNÇÃO DE FORMATAÇÃO DO FLAKE TRIPLO
---- recebe ums lista inteiros e a formata conforme a identificação do Flake triplo.

gerarTripla :: [Int] -> [String]

---- Caso base: lista vazia.
gerarTripla [] = []
---- Caso trivial: lista unitária.
gerarTripla [numeroUnico] = if numeroUnico == 10 then ["X"] else [show numeroUnico]
---- Caso genérico:
gerarTripla tripla
    ---- Strike;
    | head tripla == 10 = "X" : gerarTripla (drop 1 tripla)
    ---- Spare;
    | sum (take 2 tripla) == 10 = show (head tripla)  :  "/"  : gerarTripla (drop 2 tripla)
    ---- Padrão;
    | otherwise = show (head tripla) : show (tripla !! 2) : gerarTripla (drop 2 tripla)


-- FUNÇÃO DE OBTENÇÃO DA LISTA DE FALKES

separarFlakes :: [String] -> [[String]]

---- Caso base:
separarFlakes [] = []
---- Caso genérico:
separarFlakes lista
    | length lista == 3 = take 3 lista : separarFlakes (drop 3 lista)
    | otherwise = take 2 lista : separarFlakes (drop 2 lista)


-- FUNÇÃO MAIN - INTERFACE INPUT OUTPUT

main :: IO()

main = do

    -- Lendo entrada do usuário (formato dos casos de teste);
    pts_list <- getLine

    -- Transformando em lista de strings, ex: ["1","2","3"];
    let str_list = words pts_list

    -- Convervento a lista de strings em lista de inteiros, ex : [1,2,3];
    let int_list = (\x -> map read x :: [Int]) str_list

    -- Contando os pontos;
    let pontos = contarPontos int_list 0 0


    -- Imprimindo a saída;
    let delimitador = " | "

    putStr $
        -- Para finalizar a formatação de strings, utilizaremos esses dois mecanismos para organizar
        -- a maneira com que os flakes são representados. A função unwords percorrerá cada elemento
        -- da lista de flakes atracés do map, inserindo entre os caracteres um espaço, de modo a concluir
        -- a formatação de cada flake. Já a função intercalate recebe a lista de flakes corretamente
        -- formatada para que seja retornado uma únidca string - que separa os flakes através de um delimitador
        -- e possa ser printada através da função putStr.
        --  --> [["x y"], ] --> ["x y | ..."]
        intercalate delimitador $
        map unwords $

        -- A partir da lista de caracter gerada, cria conjuntos de caracteres - relativos a um flake.
        -- De forma que a lista de caracteres agora seja representada por uma lista de strings - flakes
        -- --> [['x','y'], ...]
        separarFlakes $

        -- Função que converte uma lista de valores inteiros para a suas respectivas representações
        -- dentro do jogo de boliche, retornando uma lista de caracteres.
        -- --> ['x' , 'y', ...]
        gerarTextoBoliche int_list

    putStr (delimitador ++ show pontos)





