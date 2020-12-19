#Das war meine Methode um das ohne IDE zu Compilieren hab es in VSCODE geschrieben
javac -classpath ./ net/jonasw/informatik/Main.java -Xlint:unchecked
java net/jonasw/informatik/Main
find ./ | grep .class | xargs rm -f
