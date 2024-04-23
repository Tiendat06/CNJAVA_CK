package com.java.service.catalog.command;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Stack;

public class ProductInvoker {
    private ICommand cmd;
    private Stack<ICommand> undoStack = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();
    private HttpServletRequest req;
    public void setCommand(ICommand command, HttpServletRequest request){
        this.cmd = command;
        this.req = request;
        HttpSession session = this.req.getSession();
        if (session.getAttribute("undoStack") == null){
            session.setAttribute("undoStack", undoStack);
        }
        if (session.getAttribute("redoStack") == null){
            session.setAttribute("redoStack", redoStack);
        }
    }

    public void executeCommand(){
        cmd.execute();
        undoStack.push(cmd);
        HttpSession session = this.req.getSession();
        session.setAttribute("undoStack", undoStack);
    }

    public void undo(){
        if (!undoStack.isEmpty()){
            HttpSession session = req.getSession();
            this.undoStack = (Stack<ICommand>) session.getAttribute("undoStack");
            ICommand c = undoStack.pop();
            c.undo();
            redoStack.push(c);
            session.setAttribute("redoStack", redoStack);
            session.setAttribute("undoStack", undoStack);
        }
    }

    public void redo(){
        if (!redoStack.isEmpty()){
            HttpSession session = req.getSession();
            this.redoStack = (Stack<ICommand>) session.getAttribute("redoStack");
            ICommand c = redoStack.pop();
            c.undo();
            undoStack.push(c);
            session.setAttribute("redoStack", redoStack);
            session.setAttribute("undoStack", undoStack);
        }
    }
}
