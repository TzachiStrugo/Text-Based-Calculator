package org.tzachi.operator;

import javax.naming.OperationNotSupportedException;

public interface BinaryEvaluator {

    int apply (int op1, int op2);
}
