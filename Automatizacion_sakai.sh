#!/bin/bash
git clone https://github.com/martiinpicss/sakai_gpi.git
mvn -f sakai_gpi/kernel compile
mvn -f sakai_gpi/kernel package
pmd check -d sakai_gpi/kernel/kernel-test/src/main/java/org/sakaiproject/test/. -R rulesets/java/quickstart.xml -f text