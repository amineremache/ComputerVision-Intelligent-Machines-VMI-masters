import sys
import rdflib
from rdflib.plugins.sparql.processor import *
import time
import os



if len(sys.argv) == 3:
  p1 = sys.argv[1]
  p2 = sys.argv[2]
else:
  p1 = sys.argv[1]
  p2 = ''




t = time.time()
g = rdflib.Graph()
g.parse("ShivPhysOnto.ttl", format="turtle")
# results = g.query(
#     """PREFIX dl: <http://ontology.dumontierlab.com/#>
#
#        SELECT DISTINCT ?cLabel ?sLabel ?comment
#        WHERE{
#         ?class rdfs:subClassOf ?super.
#         ?class rdfs:label ?cLabel.
#         ?class rdfs:comment ?comment.
#         ?super rdfs:label ?sLabel.
#         FILTER regex(?slabel,"","i")
#        }""")
#qry = input(" Please enter the search query:\n")
qry = p1+" "+p2
key_list = qry.split()
case = 0
if len(key_list) == 1:
    q = prepareQuery(
      """PREFIX dl: <http://ontology.dumontierlab.com/>

       SELECT DISTINCT ?label ?plabel ?olabel ?comment
       WHERE{
        ?class rdfs:label ?label .
        ?class ?prop ?obj .
        ?obj rdfs:label ?olabel .
        ?prop rdfs:label ?plabel .
        OPTIONAL{?class rdfs:comment ?comment}
        FILTER(regex(?label,'""" + key_list[0].strip() + """',"i")) .
       }""")
    case = 4
    resultsSub = g.query(q)
if case == 0:
    q = prepareQuery(
          """PREFIX dl: <http://ontology.dumontierlab.com/>

           SELECT DISTINCT ?label ?plabel ?olabel ?comment
           WHERE{
            ?class rdfs:label ?label .
            ?class ?prop ?obj .
            ?obj rdfs:label ?olabel .
            ?prop rdfs:label ?plabel .
            OPTIONAL{?class rdfs:comment ?comment}
            FILTER( regex(?plabel,'""" + key_list[1].strip() + """',"i") && regex(?label,'""" + key_list[0].strip() + """',"i")) .
           }
           LIMIT 10""")
    case = 1
    resultsSub = g.query(q)
if case == 0:
    q = prepareQuery(
      """PREFIX untitled-ontology-64: <http://www.semanticweb.org/vinu/ontologies/2014/3/untitled-ontology-64#>

       SELECT DISTINCT ?label ?plabel ?olabel
       WHERE{
        {
            ?class rdfs:label ?label .
            ?class ?prop ?obj .
            ?obj rdfs:label ?olabel .
            ?prop rdfs:label ?plabel .
            FILTER( regex(?olabel,'""" + key_list[1].strip() + """',"i") || regex(?label,'""" + key_list[0].strip() + """',"i")) .
        }
          UNION
        {
            ?class rdfs:label ?label .
            ?class ?prop ?obj .
            ?obj rdfs:label ?olabel .
            ?prop rdfs:label ?plabel .
            FILTER( regex(?label,'""" + key_list[1].strip() + """',"i") || regex(?olabel,'""" + key_list[0].strip() + """',"i")) .
        }
       }
       LIMIT 10""")
    resultsSub = g.query(q)
    case = 2

if case == 0:
    q = prepareQuery(
      """PREFIX dl: <http://ontology.dumontierlab.com/>

       SELECT DISTINCT ?label ?plabel ?olabel
       WHERE{
        {
            ?class rdfs:label ?label .
            ?class ?prop ?obj .
            ?obj rdfs:label ?olabel .
            ?prop rdfs:label ?plabel .
            FILTER( regex(?olabel,'""" + key_list[1].strip() + """',"i") || regex(?label,'""" + key_list[0].strip() + """',"i")) .
        }
          UNION
        {
            ?class rdfs:label ?label .
            ?class ?prop ?obj .
            ?obj rdfs:label ?olabel .
            ?prop rdfs:label ?plabel .
            FILTER( regex(?label,'""" + key_list[1].strip() + """',"i") || regex(?olabel,'""" + key_list[0].strip() + """',"i")) .
        }
       }
       LIMIT 10""")
    resultsSub = g.query(q)
    case = 3
if case == 3 and len(resultsSub) == 0:
    case = 100
# SERVICE, DESCRIBE are not implemented yet
# q1 = prepareQuery(
#       """
#        SELECT DISTINCT ?p ?o
#        WHERE
#        {
#         SERVICE <http://DBpedia.org/sparql>
#         { SELECT ?p ?o
#           WHERE
#           {
#             <http://dbpedia.org/rseource/Joseph_Hocking> ?p ?o .
#           }
#         }
#        }""")
#
# res = g.query(q1)
# for row in res:
#       print(row, end="\n--------------\n")



# results1 = g.query(
#     """PREFIX dl: <http://ontology.dumontierlab.com/>
#
#        SELECT DISTINCT ?p ?o
#        WHERE{
#         dl:Anion ?p ?o
#        }""")


def ExpandQuery(case, resultsSub):
    if case == 1:
        expQuery = set()
        for row in resultsSub:
        #    print("class: %s super:%s comment:%s" % row, end="\n------------\n")
              q = "(" +" and ".join([row["label"],row["plabel"]]) +")"
              expQuery.add(q)
              q = "(" +" and ".join([row["label"],row["olabel"]]) +")"
              expQuery.add(q)
    elif case == 2:
        expQuery = set()
        for row in resultsSub:
        #    print("class: %s super:%s comment:%s" % row, end="\n------------\n")
              q = "(" +" and ".join([row["label"],row["plabel"]]) +")"
              expQuery.add(q)
              q = "(" +" and ".join([row["plabel"],row["olabel"]]) +")"
              expQuery.add(q)
    elif case == 3:
        expQuery = set()
        for row in resultsSub:
        #    print("class: %s super:%s comment:%s" % row, end="\n------------\n")
              q = "(" +" and ".join([row["label"],row["plabel"]]) +")"
              expQuery.add(q)
              q = "(" +" and ".join([row["plabel"],row["olabel"]]) +")"
              expQuery.add(q)
    elif case == 4:
        expQuery = set()
        for row in resultsSub:
        #    print("class: %s super:%s comment:%s" % row, end="\n------------\n")
             q = "(" +" and ".join([row["label"],row["plabel"]]) +")"
             expQuery.add(q)
             q = "(" +" and ".join([row["plabel"],row["olabel"]]) +")"
             expQuery.add(q)
    else:
        expQuery = "No result"
    return " or ".join(expQuery)


#no python 3 support plus possible block of request by google . Code 403
# from xgoogle.translate import Translator
# translate = Translator().translate
# print(translate("arjuna has a father named indra", lang_to='mr', lang_from='en').encode('utf8'))
if case != 100:
    expand = ExpandQuery(case, resultsSub)
    print(expand)
    #  writing a turtle file after execution
    f = open("expansion.txt", 'w+')
    f.write(expand)

    f.close()
# f = open("c:/Users/mayan_000/desktop/foo.ttl", 'wb+')
# f.write(g.serialize(format="turtle"))
# f.close()
print(time.time() - t)
# BIND, SUBSTR works. Property paths work for non variable proeprties
