# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 19:24:35 2017

@author: ferzi
"""

fin = open('OntologyV6_fuzzy.owl', 'r', encoding="utf-8")
fout = open('Repetidos.txt', 'w', encoding="utf-8")
 
repetidos = []
for line in fin:
    if "Individual: <http://beerOntology.es/" in line:
        name = line[36:line.find(">'")-1]
        if name not in repetidos:
            repetidos.append(name)
        else:
            fout.write(name + "\n")
            
fout.close()
fin.close()