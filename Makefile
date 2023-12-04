JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
	
CLASSES = \
	Starfield.java \
	GameScreen.java \
	KeyboardManager.java \
	Bullet.java \
	Player.java \
	Asteroid.java \
	Main.java \
	Boss.java
default: classes
classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
