.PHONY: build clean run
build:
	mkdir classes;
	javac src/*.java -d classes;
	javac -cp classes src/*.java -d classes;
clean:
	rm -rf classes

