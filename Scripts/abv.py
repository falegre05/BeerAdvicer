# -*- coding: utf-8 -*-
"""
Created on Thu Aug 10 10:46:18 2017

@author: ferzi
"""

from pylab import plot,show
from numpy import dstack
from scipy.cluster.vq import kmeans,vq

f = open('OntologyV6.owl', 'r', encoding="utf-8")
abv = []
ibu = []
a = -1
i = 1
for line in f:
    if "     <http://beerOntology.es/ABV>" in line:
        a = float(line.split()[1].replace(",", ""))
        if a >= 30:
            a = -1
    if a != -1:
        abv.append(a)
        ibu.append(i)
        a = -1

data = dstack((abv, ibu))[0]

# computing K-Means with K = 2 (2 clusters)
centroids,_ = kmeans(data, 5)

# assign each sample to a cluster
idx,_ = vq(data,centroids)

# some plotting using numpy's logical indexing
plot(data[idx==0,0],data[idx==0,1],'o',
     data[idx==1,0],data[idx==1,1],'o',
     data[idx==2,0],data[idx==2,1],'o',
     data[idx==3,0],data[idx==3,1],'o',
     data[idx==4,0],data[idx==4,1],'o') # third cluster points
plot(centroids[:,0],centroids[:,1],'sm',markersize=8)
show()



f.close()