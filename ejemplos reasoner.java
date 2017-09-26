//COGER TODAS LAS CERVEZAS DE UN ESTILO
/*OWLClass clase = factory.getOWLClass("Stout", pm);
Log.d(TAG, clase.toString());
NodeSet<OWLNamedIndividual> lista = hermit.getInstances(clase, false);
Iterator iter = lista.getFlattened().iterator();
while(iter.hasNext()){
    OWLNamedIndividual ind = (OWLNamedIndividual) iter.next();
    Log.d("DEBUG", ind.toString());
}*/

//OBTENER EL VALOR DE UNA PROPIEDAD DE UN INDIVIDUO EN PARTICULAR
/*OWLNamedIndividual ind = factory.getOWLNamedIndividual("Surly_Darkness", pm);
Log.d(TAG, ind.toString());
OWLDataProperty prop = factory.getOWLDataProperty("IBU", pm);
Log.d(TAG, prop.toString());
Log.d(TAG, String.valueOf(hermit.getDataPropertyValues(ind, prop)));