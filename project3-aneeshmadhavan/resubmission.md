1) Integration Tests Implemented.

Class - IntegrationTests.java

Test 1) : An integration test that parses the Reviews and Qas file, stores the data in lists and searches for an Asin that is present in both the Reviews file and the Qas file.
Method : parseInputFileAndFindAsinInBothFiles()

Test 2) : An integration test that parses the Reviews and Qas file, stores the data in lists and searches for an Asin that is present in the Reviews file but not in the Qas file.
Method : parseInputFileAndFindAsinOnlyinReviewsTest()

Test 3) : An integration test that parses the Reviews and Qas file, stores the data in lists and searches for an Asin that is present in the Qas file but not in the Reviews file.
Method : parseInputFileAndFindAsinOnlyinQasTest()

Test 4) : An integration test that parses the Reviews and Qas file, stores the data in lists and searches for an Asin that is not present in both the Reviews file and the Qas file.
Method : parseInputFileAndFindNonExistingAsinTest()

Test 5) : An integration test that parses the Reviews file, builds the Inverted Index and searches for a term present in the Reviews file.
Method : parseInputFileAndExistingReviewSearchTest()

Test 6) : An integration test that parses the Reviews file, builds the Inverted Index and searches for a term not present in the Reviews file.
Method : parseInputFileAndNonExistingReviewSearchTest()

2) Added handlePost() methods for each Handler in the HandlerUtils class.

