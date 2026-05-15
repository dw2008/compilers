package emitter;

import java.io.*;

/**
 * Emitter prints out lines into a separate file
 * @author Anu Datar, Daniel Wu
 * @version 05/08/26
 */
public class Emitter
{
	private PrintWriter out;
	private int id;

	/**
	 * creates an emitter for writing to a new file with given name
	 * @param outputFileName the output file to output to
	 * @throws RuntimeException if IOException
	 */
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
			id = 0;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * prints one line of code to file (with non-labels indented)
	 * @param code
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
		{
			code = "\t" + code;
		}
		out.println(code);
	}

	/**
	 * closes the file.  should be called after all calls to emit.
	 */
	public void close()
	{
		out.close();
	}

	/**
	 * Pushes value of reg onto the stack
	 * @param reg the register to push from
	 */
	public void emitPush(String reg)
	{
		emit("subu $sp $sp 4");
		emit("sw " + reg + " ($sp) #push reg onto the Stack");
	}

	/**
	 * Pops off of stack and stores in reg
	 * @param reg the register to pop from
	 */
	public void emitPop(String reg)
	{
		emit("lw " + reg + " ($sp) #pop the Stack into reg");
		emit("addu $sp $sp 4");
	}

	/**
	 * Get next label for conditional jump
	 * @return id
	 */
	public int nextLabelID()
	{
		id++;
		return id;
	}
}