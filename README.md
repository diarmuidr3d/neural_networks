# neural_networks
Programming of Neural Networks for Connectionist Computing

For each test, the error rate for each epoch is stored in a txt file in the folder “/error_rates”. To be run, the tests require the libraries in “/lib”. These libraries simply provide a basic Matrix which is extended in MyMatrix.java and a CSV Reader which is used for reading in the character set. The MLP is contained in MLP.java. This class is initialised with the number of inputs, hidden units and outputs. The learn method is then called with a number of epochs, a set of examples to learn on, a learning rate and an id for the squashing function to be used (from MyMatrix.java). Following learning, outputs can be tested by calling the forward method for a particular Example input. For example here is some code used in the XOR Test:
```java
MLP mlp = new MLP(2, 3, 1);
mlp.learn(60000, examples, 0.7, MyMatrix.LOGISTIC_SQAUSH);
MyMatrix output = mlp.forward(examples[0].input, MyMatrix.LOGISTIC_SQAUSH);
```
##XOR Test
The XOR Test was completed with 3 hidden units. It takes 60000 epochs to get to a state where it is clearly predicting the required 0 and 1’s. Prior to this, the output for [1,1] is around 0.5. I’m happy with the results of this test, I think it’s quite clearly defined which elements should be 0 and which should be 1. The learning rate used was 0.7. Console output from running this test (XorTest.java) is:
```
Sin Test: Starting Learning
Learning error after 60000 epochs is 0.23455967464768462
Error for 0.7: 0.23455967464768462
Result from example 0: 0.06354428039813691 should be 0.0
Result from example 1: 0.9501917777632609 should be 1.0
Result from example 2: 0.9502278107862517 should be 1.0
Result from example 3: 0.06897856769094236 should be 0.0
```
##Sin Test
The Sin test was completed with 4 hidden units and results in quite different error rates each time it is run. The variance is likely due to the random nature of the starts. Error rate here varies from 0.5 to 1.5. As in the XOR Test, the error rate was calculated as the sum of the absolute differences between the measured and expected outputs so an error on the test set of 0.966 is the sum across ten examples, or roughly a difference of 0.0966 between the received and expected output for each example. Considering that if we predicted 0 for each example, we would have a maximum difference of 10, 0.966 is a significant improvement. The console log from running this (SinTest.java) is:
```
Sin Test: Starting Learning
Learning error after 100000 epochs is 1.8658535016560747
Error on test set: 0.9656148262051174
```
##Letter Recognition
To guess the typed character from the inputs (17 possible values for each of the 16 inputs), 10 hidden units and a learning rate of 0.01 was used. This achieved an accuracy of 66%. The inputs were passed to the MLP as is. There were 26 “binary”-style outputs, one for each character. The output character was chosen as the output with the highest score. The percentage error is simply calculated by counting the number of incorrectly predicted characters and dividing this by the size of the test set. Runtime however is rather slow, 10000 epochs can take 15 minutes. The console output from running this test (CharacterRecogniser.java) is:
```
Test set size: 4000
Learning
Learning error after 10000 epochs is 12793.699886262655
Testing
Size of test set: 4000
Number of incorrect characters: 1332.0
Percentage error (between 0 and 1): 0.333
```
