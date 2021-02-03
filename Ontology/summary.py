from google import search
import wikipedia
import os
import codecs
import sys



#get the expansion file first:

#operations on the originals file:

foo = open("original.txt", 'r')

try:
    if os.stat("original.txt").st_size != 0:
       original = foo.read().split(" ")
       res = "something"
    else:
       res = "No results found!"
except OSError:
    res = "Expansion file missing!"




#OPERATION ON EXPANSION FILE
f = open("expansion.txt", 'r')
res = "No results found!"

try:
    if os.stat("expansion.txt").st_size != 0:
       word = f.read().split(") or (")
       res = "something"
    else:
       res = "No results found!"
except OSError:
    res = "Expansion file missing!"


mylist = []


#set true or false depending on which code y'all wanna use!
codew = False
codeg = True
#//----WIKIPEDIA CODE HERE! ------------
#works if codew is true
if codew:
    for w in word:
      # remove some minor errors
      w = w.replace(")", "")
      w = w.replace("(", "")
      sub = w.split(" and ")
      for s in sub:
              mylist.append(s)


#//-------GOOGLE CODE HERE--------------
#works if codeg is true
if codeg:
    for w in word:
      # remove some minor errors
      w = w.replace(")", "")
      w = w.replace("(", "")
      w = w.replace(" and", "")
      mylist.append(w)

#--------------------------------------------------------------------------
mylist = list(set(mylist))
print(mylist)

result = []

file = codecs.open("example.txt", "ab+", "utf-8")
final = []

for w in mylist:
        try:
            if codew:
                article = wikipedia.summary(w, sentences=5, redirect=True, auto_suggest=True)
                file.write("<h2>"+w+"</h2><br><p>"+article+"</p>")
                file.write("<br>\n")
            if codeg:
                for url in search(w+' wikipedia', stop=1):
                   if "wikipedia" in url:
                       result.append(url)

                final.append(result[0])
                result[:] = []

                # if any(x in article for x in original):
                #     file.write("<h2>"+w+"</h2><br><p>"+article+"</p>")
                #     file.write("<br>\n")

        except:
                pass


if codeg:
    final = list(set(final))
    result = list(set(result))

    for r in final:
       #print(r)
       query = r
       #print(query[30:])
       encoded = query[30:]
       article = wikipedia.summary(encoded, sentences=3)
       file.write("<h2>"+encoded.replace("_", " ")+"</h2><br><p>"+article+"</p>")
       file.write("<br>\n")



open("original.txt", 'w').close()
