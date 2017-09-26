# -*- coding: utf-8 -*-
"""
Created on Fri Jun  9 19:10:10 2017

@author: ferzi
"""
import ratebeer
import time

start_time = time.time()

rb = ratebeer.RateBeer()

base = "<http://beerOntology.es/"

exceptions = open('exceptions.txt', 'w', encoding="utf-8")

f = open('version6.owl', 'w', encoding="utf-8")

f.write("Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n")
f.write("Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n\n\n\n")
f.write("Ontology: <http://beerOntology.es/>\n\n\n")
f.write("Datatype: xsd:double\n\n\n")
f.write("Datatype: rdfs:Literal\n\n\n")
f.write("Datatype: xsd:integer\n\n\n")
f.write("DataProperty: <http://beerOntology.es/ABV>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\txsd:double\n\n\n")
f.write("DataProperty: <http://beerOntology.es/IBU>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\txsd:integer\n\n\n")
f.write("DataProperty: <http://beerOntology.es/overall_rating>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\txsd:integer\n\n\n")
f.write("DataProperty: <http://beerOntology.es/style_rating>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\txsd:integer\n\n\n")
f.write("DataProperty: <http://beerOntology.es/img>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\trdfs:Literal\n\n\n")
f.write("DataProperty: <http://beerOntology.es/brewery>\n\n")
f.write("\tDomain: \n")
f.write("\t\t<http://beerOntology.es/Beer>\n\n")
f.write("\tRange:\n")
f.write("\t\trdfs:Literal\n\n\n")
f.write("Class: "+ base + "Beer>\n\n")
f.write("Class: "+ base + "Ale>\n\n")
f.write("\tSubClassOf:\n\t\t" + base + "Beer>\n\n\n")
f.write("Class: "+ base + "Lager>\n\n")
f.write("\tSubClassOf:\n\t\t" + base + "Beer>\n\n\n")
f.write("Class: "+ base + "Specialty>\n\n")
f.write("\tSubClassOf:\n\t\t" + base + "Beer>\n\n\n")


noValen =[104, 83, 44, 8, 10, 107, 112, 87, 89, 91, 86, 85, 94, 84, 92, 90, 93, 95, 88] 
num = 0;
styles = rb.beer_style_list()
for k, v in styles.items():
    if v not in noValen:
            k = k.replace(" ","_").replace("/", "-")
            f.write("Class: "+ base + k + ">\n\n")
            if "_Ale" in k:
                f.write("\tSubClassOf:\n\t\t" + base + "Ale>\n\n")
            elif "_Lager" in k:
                f.write("\tSubClassOf:\n\t\t" + base + "Lager>\n\n")
            else:
                f.write("\tSubClassOf:\n\t\t" + base + ">\n\n")
                
            try:
                for beer in rb.beer_style(v, sort_type="score", sort_order="descending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                        
                for beer in rb.beer_style(v, sort_type="score", sort_order="ascending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                        
                for beer in rb.beer_style(v, sort_type="count", sort_order="descending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                        
                for beer in rb.beer_style(v, sort_type="count", sort_order="ascending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                        
                for beer in rb.beer_style(v, sort_type="abv", sort_order="descending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                        
                for beer in rb.beer_style(v, sort_type="abv", sort_order="ascending"):
                    if not beer.retired and beer.abv:
                        f.write("Individual: " + base + beer.name.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tTypes: \n")
                        f.write("\t\t" + base + beer.style.replace(" ","_").replace("/", "-") + ">\n\n")
                        f.write("\tFacts:\n")
                        f.write("\t\t" + base + "ABV>  "  + str(beer.abv) + ",\n")
                        f.write("\t\t" + base + "img>  \""  + beer.img_url + "\",\n")
                        if beer.ibu:
                            f.write("\t\t" + base + "IBU>  "  + str(beer.ibu) + ",\n")
                        f.write("\t\t" + base + "brewery>  \""  + str(beer.brewery) + "\",\n")
                        f.write("\t\t" + base + "overall_rating>  "  + str(beer.overall_rating) + ",\n")
                        f.write("\t\t" + base + "style_rating>  "  + str(beer.style_rating) + "\n\n")
                        num += 1
                        print(num)
                    
            except:
                exceptions.write(beer.name + "\n")

            
f.close()
exceptions.close()
print("--- %s seconds ---" % (time.time() - start_time))
