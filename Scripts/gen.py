#!/usr/env/python

import xml.etree.ElementTree as ET

base = "<http://beerOntology.es/"

f = open('ontology.owl', 'w')
f.write("Ontology: <http://beerOntology.es/>\n\n")
f.write("Class: "+ base + "beer>\n\n")

tree = ET.parse("ontology.xml")
root = tree.getroot()
print(root.tag)

for beerFamily in root:
    f.write("Class: "+ base + beerFamily.tag + ">\n\n")
    f.write("\tSubClassOf:\n\t\t" + base + "beer>\n\n")
    print("\t" + beerFamily.tag, beerFamily.attrib)
    
    for beerClass in beerFamily:
        f.write("Class: "+ base + beerClass.tag + ">\n\n")
        f.write("\tSubClassOf:\n\t\t" + base + beerFamily.tag + ">\n\n")
        print("\t\t" + beerClass.tag, beerClass.attrib)
        
        
        for field in beerClass:
            if field.tag == "Samples":
                for beerStyle in field:
                    f.write("Class: "+ base + beerStyle.tag + ">\n\n")
                    f.write("\tSubClassOf:\n\t\t" + base + beerClass.tag + ">\n\n")
                    print("\t\t\t" + beerStyle.tag, beerStyle.attrib)
                    
        if "NoGroup" in beerClass.tag:
            for beerStyle in beerClass:
                f.write("Class: "+ base + beerStyle.tag + ">\n\n")
                f.write("\tSubClassOf:\n\t\t" + base + beerClass.tag + ">\n\n")
                print("\t\t\t" + beerStyle.tag, beerStyle.attrib)

        
        
f.close()
        