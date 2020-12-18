javac -classpath ./ net/jonasw/informatik/Main.java
java net/jonasw/informatik/Main
find ./ | grep .class | xargs rm -f
