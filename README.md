# SID-ES2-05
Afonso Miao Nº78167

João Garcez Nº78530

Francisco Arruda Nº77974

Miguel Gil Nº78107

Beatriz Patrício Nº78318

Pedro Norte Nº78160


Link github projeto android: https://github.com/fmoaa-iscteiul/SID-ES2-05-ANDROID

# Entrega Do Trabalho
O grupo decidiu entregar o trabalho numa máquina virtual UBuntu, a mesma possui o xampp, mongodb e o eclipse instalados. Os passos a seguir são:

1-Iniciar a terminal, usando CTRL + ALT + T

2-Para iniciar o servidor mongodb pela terminal, executar este comando:
  --> sudo service mongod start
  --> Para verificar se o servidor está ativo, usar o comando: sudo service mongod status

3-Para iniciar o xampp pela terminal: (Executar os seguintes comandos)
  --> cd /opt/lampp
  --> sid ./manager-linux-x64.run
  NOTA: Se por alguma razão não tiver permissão para executar, utilize este comando para ter permissoões de execução: sudo chmod 755 manager-linux-x64.run

4-Depois de o xampp se ter inicializado, vá para o tab "Manage Servers" e ligue MySQLDatabase e ApacheWebServer

5-No canto inferior esquerdo, clique no "Show Applications" ou no icone que possui 4 Pontos. A partir daí pode escrever eclipse na barra de pesquisa e corra o eclipse.

6-Depois de ter o eclipse ligado:
  --> Para testar a interface gráfica, corra a classe GUI_Login
  --> Para testar a migração dos dados para o mongodb, corra a classe Subscriber

Nota:Se por alguma razão quiser aceder a base de dados através do PhpMyAdmin como "root" a password é: teste123.Deixamos aqui nomes de utilizadores e as suas respetivas passwords para utilizar na interface gráfica:

afonso

teste123

joao

teste123
