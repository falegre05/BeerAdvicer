# -*- coding: utf-8 -*-
"""
Created on Thu Jun  8 12:21:02 2017

@author: ferzi
"""
import xml.etree.ElementTree as ET
import ratebeer

base = "<http://beerOntology.es/"

rb = ratebeer.RateBeer()

faltan = open('faltan.txt', 'w')
f = open('individuals.owl', 'w')
exceptions = open('exceptions.txt', 'w')


tree = ET.parse("ontology.xml")
root = tree.getroot()
print(root.tag)

for beerFamily in root:
    print("\t" + beerFamily.tag, beerFamily.attrib)
    for beerClass in beerFamily:
        print("\t\t" + beerClass.tag, beerClass.attrib)
        for field in beerClass:
            if field.tag == "Samples":
                for beerStyle in field:
                    print("\t\t\t" + beerStyle.tag, beerStyle.attrib)
                    beers = str(beerStyle.find('Popular_Examples').text)
                    for beer in beers.split(', '):
                        query = str(rb.search(beer))
                        if len(query) <= 30:
                            faltan.write(beer + "\n")
                        else:
                            fin = query[35:].find('\'', 1)
                            url = query[35:35+fin]
                            try:
                                beer_info = rb.beer(url)
                                for k, v in beer_info.items():
                                    if k == "abv":
                                        abv = v
                                        f.write("Individual: " + base + 
                                                beer.replace(" ","_") + ">\n\n")
                                        f.write("\tTypes: \n")
                                        f.write("\t\t" + base + beerStyle.tag + ">\n\n")
                                        f.write("\tFacts:\n")
                                        f.write("\t\t" + base + "ABV>  "  + str(abv) + "\n\n")
                            except:
                                exceptions.write(beer + "\n")
        if "NoGroup" in beerClass.tag:
            for beerStyle in beerClass:
                print("\t\t\t" + beerStyle.tag, beerStyle.attrib)
                beers = str(beerStyle.find('Popular_Examples').text)
                for beer in beers.split(', '):
                    query = str(rb.search(beer))
                    if len(query) <= 30:
                        faltan.write(beer + "\n")
                    else:
                        fin = query[35:].find('\'', 1)
                        url = query[35:35+fin]
                        print (url, "\n")
                        try:
                            beer_info = rb.beer(url)
                            for k, v in beer_info.items():
                                if k == "abv":
                                    abv = v
                                    f.write("Individual: " + base + 
                                    beer.replace(" ","_") + ">\n\n")
                                    f.write("\tTypes: \n")
                                    f.write("\t\t" + base + beerStyle.tag + ">\n\n")
                                    f.write("\tFacts:\n")
                                    f.write("\t\t" + base + "ABV>  "  + str(abv) + "\n\n")
                        except:
                                exceptions.write(beer + "\n")
                    
faltan.close()
f.close()
exceptions.close()