#!/bin/bash
source ./SBProjeto.prop
docker exec  devopsTestes1 mysqladmin -u root -psenhaDev#123 create $NOME_BANCO
docker exec  devopsTestes1  /bin/bash -c  'mysql -uroot -psenhaDev#123 '$NOME_BANCO' < /devopsDBScript/'$NOME_BANCO'.Homologacao.sql'