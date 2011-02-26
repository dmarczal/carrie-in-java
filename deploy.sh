tar cvzf fractal_retro.tar.gz html/

scp fractal_retro.tar.gz diego@ssh.c3sl.ufpr.br:

rm fractal_retro.tar.gz

if ! [ -z $1 ]; then
  if [ $1 = "uca" ]; then 
	ssh diego@ssh.c3sl.ufpr.br "scp fractal_retro.tar.gz condigital@cognac:www/fractal_retro_uca && ssh condigital@cognac 'cd www/fractal_retro_uca && rm index.html && rm -rf jars/ && tar -xvzf fractal_retro.tar.gz && rm fractal_retro.tar.gz && mv -f html/* . && rm -rf html/'"
  fi

  if [ $1 = "edu" ]; then 
	ssh diego@ssh.c3sl.ufpr.br "scp fractal_retro.tar.gz root@educacional:/home/apps/dev/educacional.c3sl.ufpr.br/public/ && ssh root@educacional 'cd /home/apps/dev/educacional.c3sl.ufpr.br/public/fractal && rm -rf * && mv ../fractal_retro.tar.gz . && tar -xvzf fractal_retro.tar.gz && rm fractal_retro.tar.gz && mv -f html/* . && rm -rf html/'"
  fi

else

ssh diego@ssh.c3sl.ufpr.br "scp fractal_retro.tar.gz condigital@cognac:www/fractal_retro && ssh condigital@cognac 'cd www/fractal_retro && rm index.html && rm -rf jars/ && tar -xvzf fractal_retro.tar.gz && rm fractal_retro.tar.gz && mv -f html/* . && rm -rf html/'"

fi
