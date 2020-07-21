package pccontroller;

import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class MainTest {

    @Test
    @Disabled
    @DisplayName("Some test")
    void test() {
        Assert.assertTrue(true);
        String[] str = {};
        Main.main(str);
    }
}


/**

By default , the JUnit engine instantiates the test class for each test case(method)

 Things to avoid

 1.The test cases should be independent , the execution or the result of a test case
 should not be influenced by the execution or the result of another test

 2.The tests should not have shared state unless the shared state is reinitialized before each
 test case execution(i.e using the @BeforeAll hook)


 Lifecycle hooks

 @BeforeAll
 @AfterAll
 @BeforeEach
 @AfterEach

 The beforeAll hook is called before any instance of the test class is created
 The afterAll hook is called after all the instances of the class have been destroyed

 That's why the methods annotated with @BeforeAll and @AfterAll must be static


 @TestInstance annotation : specifies how the JUnit engine should instantiate the test class
 The default value is TestInstance.Lifecycle.PER_METHOD


 Syntax

 @TestInstance(TestInstance.Lifecycle.PER_METHOD)

 Things to note

 1. The test methods in the test class do not follow any specific order

 If you try to initialize a value A in method X and then use that updated value a in another
 method Y there is no guarantee that method X will be called before method Y and you may end up
 processing the same value in method Y,and then the value will be updated in method X


 2. If you configure JUnit engine to instantiate a single test class for all the test cases
 (TestInstance.Lifecycle.PER_CLASS) then the @BeforeAll and @After all methods doesn't
 necessarily need to be static because only one instance of the class is created



 Annotations

 @DisplayName("Some name") -> the diplayed name for a test (by default the diplay name is the method name)
 @Disabled -> used to specify that a test should be disabled (very useful , rather than commenting out all the code)


 Conditional execution -> a way to execute certain code/tests in certain situations


 Useful annotations

 @EnabledOnOs(OS.LINUX) -> the test will run only on Linux systems(on another os -> disabled)
 @EnableOnJre(JRE.JAVA_11) -> runs only a specific jre version
 @EnableIf
 @EnableIfSystemProperty
 @EnableIfEnvironmentVariable

 Assumptions.assumeTrue(some assumed stuff) -> if the assumption is not correct then the test will be ignored
 This gives programatic control over the test execution


 Using assertAll

 assertAll can be used if you only need to do a bunch of assertions


 assertAll takes in a bunch of lambdas and asserts all of them
 so you don't need to write assert statements multiple times



 assertAll (
 () -> { some assertion }
 () -> {some other assertion}
 );


 Writing nested test classes
@Nested annotation

Nested tests are useful when you need to write stuff that tests the same base functionality
 but the context of the text execution is different.
 For example , if you need to test if you add function performs well , you can write multiple
 tests and you can use positive numbers as parameters or negative numbers as parameters



 @Nested
 @DiplayName("Some readable name")

 class NestedTestClass {
        // Mehods and stuff

        @Test
        @DisplayName("Test for certain stuff")
        void test () {
        // do stuff
        }

        @Test
        @DisplayNamer("Test for another stuff")
        void other_test() {
        // do_other_stuff
        }
 }

 Things to note

 If any of the nested tests fails , the wrapper class for that tests failsw



 Using supplier for assert messages


 For every text execution , you can optimize the allocated resources.
 For example , the string that will be shown if the test fails will be
 created although the test passes(the string creation could be a computational expensive operation)


 This is why supplier lambdas can be used instead of strings in order to do lazy evaluation of assert messages


 assertEquals(true,false,() -> "The values are not equal(if the test would have passed , this message wasn't shown)")


 Using repeated test
        -> repeat a test for a certain number of times

 @RepeatedTest(10)
void test(Repetition info) {
    sout(info.getCurrentRepetition());
    //do_stuff
 }



 Tagging tests
 @Tag("Some tag")
 A way to run only certain tests

 TestInfo and TestReporter
    Dependency injection stuff

 TestInfo ->
 TestReporter ->

 @BeaforeEach

 void init(TestInfo testInfo , TestReporter testReporter) {
    // do stuff with testInfo and testReporter
 }

 f
 */