tar cvzf fractal_retro.tar.gz html/

scp fractal_retro.tar.gz diego@ssh.c3sl.ufpr.br:

rm fractal_retro.tar.gz

ssh diego@ssh.c3sl.ufpr.br "scp fractal_retro.tar.gz condigital@cognac:www/fractal_retro && ssh condigital@cognac 'cd www/fractal_retro && rm index.html && rm -rf jars/ && tar -xvzf fractal_retro.tar.gz && rm fractal_retro.tar.gz && mv -f html/* . && rm -rf html/'"
