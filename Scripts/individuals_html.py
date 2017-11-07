# -*- coding: utf-8 -*-
"""
Created on Mon Oct 30 10:48:21 2017

@author: ferzi
"""

import ratebeer

rb = ratebeer.RateBeer()

fout = open('individuals.owl', 'w', encoding="utf-8")
fin = open('chapas.htm', 'r', encoding="utf-8")

base = "<http://beerOntology.es/"

i = 0
for line in fin:
    if i % 11 == 0 and "<td>" in line :
        if 'href' in line:
            cerveza = line[line.find("jpg'")+5:line.find("</a>")]
        else:
            cerveza = line[6:line.find("</td>")]
        print(cerveza)  
        query = str(rb.search(cerveza))
        if len(query) > 30:
            fin = query[35:].find('\'', 1)
            url = query[35:35+fin]
            beer = rb.get_beer(url)
            try:
                if not beer.retired and beer.abv:
                    fout.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                    fout.write("\tTypes: \n")
                    fout.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                    fout.write("\tFacts:\n")
                    fout.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                    fout.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                    if beer.ibu:
                        fout.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                    fout.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                    fout.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                    fout.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
            except:
                print("ERRORACO CON LA URL " + url)
    
    
    i = i + 1



fout.close()
fin.close()