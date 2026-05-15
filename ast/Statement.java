package ast;

import emitter.Emitter;

/**
 * Definition for abstract class Statement for ast
 * @author Daniel Wu
 * @version 05/08/26
 */
public abstract class Statement
{
    /**
     * Skeleton for compile, if not implemented will throw error
     * @param e the emitter to use
     */
    public void compile (Emitter e)
    {
        throw new RuntimeException("IMPLEMENT ME NOW");
    }
}
