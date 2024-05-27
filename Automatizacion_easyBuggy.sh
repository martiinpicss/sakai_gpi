git clone https://github.com/k-tamura/easybuggy.git
mvn -f easybuggy compile
mvn -f easybuggy package
pmd check -d easybuggy/. -R rulesets/java/quickstart.xml -f text