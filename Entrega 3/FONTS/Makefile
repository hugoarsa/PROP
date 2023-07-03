CLASS_INPUT = ./src/main/java/Exceptions/*.java ./src/main/java/Domain/*.java ./src/main/java/Persistence/*.java ./src/main/java/Presentation/*.java ./src/main/java/Drivers/*.java 
CLASS_OUTPUT = ../EXE/classFiles
JAR_OUTPUT = ../EXE/
JUNIT_JARS = ../lib/junit-4.12.jar:../lib/hamcrest-core-1.3.jar
JUNIT_TESTS = ./src/test/java/*.java

all: 
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) -Xlint:unchecked
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS) 

classfiles:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) 
	
jars:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./VectorMEV.mf $(JAR_OUTPUT)DriverVectorMEV.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./ArchivoCtrl.mf $(JAR_OUTPUT)DriverArchivoCtrl.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./CjtAutores.mf $(JAR_OUTPUT)DriverCjtAutores.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./Corpus.mf $(JAR_OUTPUT)DriverCorpus.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./DomainCtrl.mf $(JAR_OUTPUT)DriverDomainCtrl.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./ExpBooleana.mf $(JAR_OUTPUT)DriverExpBooleana.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./ExpBooleanaCtrl.mf $(JAR_OUTPUT)DriverExpBooleanaCtrl.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./UsuarioCtrl.mf $(JAR_OUTPUT)DriverUsuarioCtrl.jar -C $(CLASS_OUTPUT) .

jarMain:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./main.mf $(JAR_OUTPUT)main.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)main.jar 

jarVectorMEV:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./VectorMEV.mf $(JAR_OUTPUT)DriverVectorMEV.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverVectorMEV.jar

jarArchivoCtrl:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./ArchivoCtrl.mf $(JAR_OUTPUT)DriverArchivoCtrl.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverArchivoCtrl.jar

jarCjtAutores:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./CjtAutores.mf $(JAR_OUTPUT)DriverCjtAutores.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverCjtAutores.jar

jarCorpus:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./Corpus.mf $(JAR_OUTPUT)DriverCorpus.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverCorpus.jar

jarDomainCtrl:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./DomainCtrl.mf $(JAR_OUTPUT)DriverDomainCtrl.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverDomainCtrl.jar

jarExpBooleana:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./ExpBooleana.mf $(JAR_OUTPUT)DriverExpBooleana.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverExpBooleana.jar

jarExpBooleanaCtrl:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./ExpBooleanaCtrl.mf $(JAR_OUTPUT)DriverExpBooleanaCtrl.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverExpBooleanaCtrl.jar

jarUsuarioCtrl:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./UsuarioCtrl.mf $(JAR_OUTPUT)DriverUsuarioCtrl.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)DriverUsuarioCtrl.jar

junitTests:
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)

unitTestArchivo: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore ArchivoTest

unitTestArchivoCtrl: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore ArchivoCtrlTest
 
unitTestCjtAutores: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore CjtAutoresTest

unitTestCorpus: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore CorpusTest

unitTestDomainCtrl: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore DomainCtrlTest

unitTestExpBooleana: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore ExpBooleanaTest

unitTestExpBooleanaCtrl: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore ExpBooleanaCtrlTest

unitTestUsuario: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore UsuarioTest

unitTestUsuarioCtrl: all
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore UsuarioCtrlTest

