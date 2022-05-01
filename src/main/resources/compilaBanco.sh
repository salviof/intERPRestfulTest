#!/bin/bash
source ./SBProjeto.prop
docker exec devopsTestes1  /bin/bash -c  'mysqldump -u root -psenhaDev#123  '$NOME_BANCO' > /devopsDBScript/'$NOME_BANCO'.Homologacao.sql'
#mysqldump -u root -psenhaDev#123 $NOME_BANCO > /devopsDBScript/$NOME_BANCO.Homologacao.sql