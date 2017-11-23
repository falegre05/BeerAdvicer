# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 17:20:17 2017

@author: ferzi
"""

import json


fout = open('spanishBeers.owl', 'w', encoding="utf-8")

base = "<http://beerOntology.es/"

with open('spanishBeers.json', encoding="utf-8") as json_data:
    fj = json.load(json_data)
    
    for beer in fj["beers"]:
        print(beer["name"])
        fout.write("Individual: " + base + beer["name"].replace(" ","_").replace("/", "-") + ">\n\n")
        fout.write("\tTypes: \n")
        fout.write("\t\t" + base + beer["style"]["name"].replace(" ","_").replace("/", "-") + ">\n\n")
        fout.write("\tFacts:\n")
        fout.write("\t\t" + base + "ABV>  "  + str(round(beer["abv"],1)) + ",\n")
        fout.write("\t\t" + base + "brewery>  \""  + str(beer["brewer"]["name"]) + "\",\n")
        if "Commercial" in beer["brewer"]["type"]:
            fout.write("\t\t" + base + "industrial>  true,\n")
        else:
            fout.write("\t\t" + base + "industrial>  false,\n")
        if beer["overallScore"] != None:
            fout.write("\t\t" + base + "overall_rating>  "  + str(round(beer["overallScore"])) + ",\n")
        fout.write("\t\t" + base + "country>  "  + base + str(beer["brewer"]["country"]["name"]) + ">,\n")
        if beer["styleScore"] != None:
            fout.write("\t\t" + base + "style_rating>  "  + str(round(beer["styleScore"])) + ",\n")
        fout.write("\t\t" + base + "img>  \""  + beer["imageUrl"] + "\"\n\n")
        
fout.close()
        