# -*- coding: utf-8 -*-
"""
Created on Sun Nov 19 21:03:19 2017

@author: ferzi
"""

import ratebeer
import time

start_time = time.time()
rb = ratebeer.RateBeer()

base = "<http://beerOntology.es/"

exceptions = open('exceptions.txt', 'w', encoding="utf-8")
ontology = open('OntologyV6_fuzzy.owl', 'r', encoding="utf-8")
f = open('Ontology_brew.owl', 'w', encoding="utf-8")
fcountries = open('Countries.txt', 'w', encoding="utf-8")


num = 1

for line in ontology:
    if "Individual: <http://beerOntology.es/" in line:
        f.write(line)
        query = str(rb.search((line[36:line.find(">'")-1]).replace("_", " ")))
        if len(query) > 30:
            fin = query[35:].find('\'', 1)
            url = query[35:35+fin]
            beer = rb.get_beer(url)
            country = beer.brewery.country
            type2 = beer.brewery.type
            fcountries.write(str(country) + "\n")
            print(str(num) + " " + beer.name)
            num = num + 1
            
    elif "Facts:" in line:
        f.write(line)
        f.write(" \t" + base + "country>  \""  + base + str(country) + "\",\n")
        if type2 == "Microbrewery":
            f.write(" \t" + base + "industrial>  false,\n")
        else:
            f.write(" \t" + base + "industrial>  true,\n")
            
    else:
        f.write(line)



fcountries.close()
ontology.close()
f.close()
exceptions.close()
print("--- %s seconds ---" % (time.time() - start_time))