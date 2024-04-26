package ps1;

import java.util.Stack;

/** Cons is a simple cons cell record type. */
// class Cons {
//     RatPoly head;
//     Cons tail;
//     Cons(RatPoly h, Cons t) { head = h; tail = t; }
// }

/** <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
    <p>
    Each RatPolyStack can be described by [p1, p2, ... ], where [] is
    an empty stack, [p1] is a one element stack containing the Poly
    'p1', and so on.  RatPolyStacks can also be described
    constructively, with the append operation, ':'. such that [p1]:S
    is the result of putting p1 at the front of the RatPolyStack S.
    <p>
    A finite sequence has an associated size, corresponding to the
    number of elements in the sequence.  Thus the size of [] is 0, the
    size of [p1] is 1, the size of [p1, p1] is 2, and so on.
    <p>
    Note that RatPolyStack is similar to <i>lists</i> like {@link
    java.util.ArrayList} with respect to its abstract state (a finite
    sequence), but is quite different in terms of intended usage.  A
    stack typically only needs to support operations around its top
    efficiently, while a vector is expected to be able to retrieve
    objects at any index in amortized constant time.  Thus it is
    acceptable for a stack to require O(n) time to retrieve an element
    at some arbitrary depth, but pushing and popping elements should
    be O(1) time.

*/
public class RatPolyStack {

    private Stack<RatPoly> polys; // head of list

    // Definitions:
    //   For a Cons c, let Seq(c) be [] if c == null,
    //                               [c.head]:Seq(c.tail) otherwise
    //                     Count(c) be 0 if c == null,
    //                                 1 + Count(c.tail) otherwise
    //
    // (These are helper functions that will make it easier for us
    // to write the remainder of the specifications.  They are
    // seperated out because the nature of this representation lends
    // itself to analysis by recursive functions.)

    // Abstraction Function:
    //   RatPolyStack s models Seq(s.polys)
    // (This explains how we can understand what a Stack is from its
    // 'polys' field.  (Though in truth, the real understanding comes
    // from grokking the Seq helper function).)

    // RepInvariant:
    //   s.size == Count(s.polys)
    // (This defines how the 'size' field relates to the 'polys'
    // field.  Notice that s.polys != null is *not* a given Invariant;
    // this class, unlike the RatPoly class, allows for one of its
    // fields to reference null, and thus your method implementations
    // should not assume that the 'polys' field will be non-null on
    // entry to the method, unless some other aspect of the method
    // will enforce this condition.)

    /** @effects Constructs a new RatPolyStack, [].
     */
    public RatPolyStack() {
        polys = null;
        assert(checkRep());
    }

    /** Pushes a RatPoly onto the top of this.
        @requires p != null
        @modifies this
        @effects this_post = [p]:this
    */
    public void push(RatPoly p) {
        assert(p != null);
        if(polys == null)
            polys = new Stack<RatPoly>();
        polys.push(p);
        assert(checkRep());
    }

    /** Removes and returns the top RatPoly.
        @requires this.size() > 0
        @modifies this
        @effects If this = [p]:S
        then this_post = S && returns p
    */
    public RatPoly pop() {
        assert(polys.size() > 0);
        RatPoly p = polys.pop();
        assert(checkRep());
        return p;
    }

    /** Duplicates the top RatPoly on this.
        @requires this.size() > 0
        @modifies this
        @effects If this = [p]:S
        then this_post = [p, p]:S
    */
    public void dup() {
        assert(polys.size() > 0);
        RatPoly p = polys.peek();
        polys.push(p);
        assert(checkRep());
    }

    /** Swaps the top two elements of this.
        @requires this.size() >= 2
        @modifies this
        @effects If this = [p1, p2]:S
        then this_post = [p2, p1]:S
    */
    public void swap() {
        assert(polys.size() >= 2);
        RatPoly p1 = polys.pop();
        RatPoly p2 = polys.pop();
        polys.push(p1);
        polys.push(p2);
        assert(checkRep());
    }

    /** Clears the stack.
        @modifies this
        @effects this_post = []
    */
    public void clear() {
        polys.clear();
        assert(checkRep());
    }

    /** Returns the RatPoly that is 'index' elements from the top of
        the stack.
        @requires index >= 0 && index < this.size()
        @effects If this = S:[p]:T where S.size() = index, then
        returns p.
    */
    public RatPoly get(int index) {
        assert(index >= 0 && index < polys.size());
        return polys.get(polys.size() - index - 1);
    }

    /** Adds the top two elements of this, placing the result on top
        of the stack.
        @requires this.size() >= 2
        @modifies this
        @effects  If this = [p1, p2]:S
        then this_post = [p3]:S
        where p3 = p1 + p2
    */
    public void add() {
        assert(polys.size() >= 2);
        RatPoly p1 = polys.pop();
        RatPoly p2 = polys.pop();
        polys.push(p1.add(p2));    
        assert(checkRep());    
    }

    /** Subtracts the top poly from the next from top poly, placing
        the result on top of the stack.
        @requires this.size() >= 2
        @modifies this
        @effects  If this = [p1, p2]:S
        then this_post = [p3]:S
        where p3 = p2 - p1
    */
    public void sub() {
        assert(polys.size() >= 2);
        RatPoly p1 = polys.pop();
        RatPoly p2 = polys.pop();
        polys.push(p2.sub(p1));
        assert(checkRep()); 
    }

    /** Multiplies top two elements of this, placing the result on
        top of the stack.
        @requires this.size() >= 2
        @modifies this
        @effects  If this = [p1, p2]:S
        then this_post = [p3]:S
        where p3 = p1 * p2
    */
    public void mul() {
        assert(polys.size() >= 2);
        RatPoly p1 = polys.pop();
        RatPoly p2 = polys.pop();
        polys.push(p1.mul(p2));
        assert(checkRep()); 
    }

    /** Divides the next from top poly by the top poly, placing the
        result on top of the stack.
        @requires this.size() >= 2
        @modifies this
        @effects  If this = [p1, p2]:S
        then this_post = [p3]:S
        where p3 = p2 / p1
    */
    public void div() {
        assert(polys.size() >= 2);
        RatPoly p1 = polys.pop();
        RatPoly p2 = polys.pop();
        polys.push(p2.div(p1));
        assert(checkRep()); 
    }

    /** Integrates the top element of this, placing the result on top
        of the stack.
        @requires this.size() >= 1
        @modifies this
        @effects  If this = [p1]:S
        then this_post = [p2]:S
        where p2 = indefinite integral of p1 with integration constant 0
    */
    public void integrate() {
        assert(polys.size() >= 1);
        RatPoly p1 = polys.pop();
        polys.push(p1.antiDifferentiate(new RatNum(0)));
        assert(checkRep()); 
    }

    /** Differentiates the top element of this, placing the result on top
        of the stack.
        @requires this.size() >= 1
        @modifies this
        @effects  If this = [p1]:S
        then this_post = [p2]:S
        where p2 = derivative of p1
    */
    public void differentiate() {
        assert(polys.size() >= 1);
        RatPoly p1 = polys.pop();
        polys.push(p1.differentiate());
        assert(checkRep()); 
    }

    /** Returns the number of RayPolys in this RatPolyStack.
        @return the size of this sequence.
     */
    public int size() {
        if(polys == null)
            return 0;
        else
            return polys.size();
    }

    /** Checks to see if the representation invariant is being violated and if so, throws RuntimeException
        @throws RuntimeException if representation invariant is violated
    */
    private boolean checkRep() throws RuntimeException {

        if(polys == null) {
            if(this.size() != 0){
                // throw new RuntimeException("size field should be equal to zero when polys is null sine stack is empty");
                System.out.println("size field should be equal to zero when polys is null sine stack is empty");
                return false;
            }
        } else {

            int countResult = 0;
            // RatPoly headPoly = polys.peek();
            Stack<RatPoly> nextCons = polys;

            // if(headPoly != null) { 
            //     for (int i = 1;; i++) {
            //         if(nextCons != null) {
            //             countResult = i;
            //             nextCons = nextCons.tail;
            //         } else
            //             break;                
            //     }
            // }
            if(nextCons.size() != this.size()){
                // throw new RuntimeException("size field is not equal to Count(s.polys). Size constant is "+size+" Cons cells have length "+countResult);   
                System.out.println("size field is not equal to Count(s.polys). Size constant is "+this.size()+" Cons cells have length "+countResult);
                return false;
            }
        }
        return true;
    }
}
