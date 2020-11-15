#
# Makefile geral para a triangulação de poligonos
#

# Gera todos os .class
all: dirclasses

# Make nas classes gerais
dirclasses:
	cd src; make

# Apaga arquivos compilados
clean:
	cd src; make clean

