/**
 *  Copyright (c) 1999-2011, Ecole des Mines de Nantes
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the Ecole des Mines de Nantes nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package solver.constraints.nary.nogood;

import solver.exception.ContradictionException;
import solver.variables.IntVar;
import util.ESat;

/**
 * A class to define a Nogood of size == 1.
 * <p/>
 * Made of a list of variables, a list of values and an int.
 * {vars, values} matches positive decisions.
 * A positive decision d_i is vars_i=values_i.
 * <p/>
 * Related to "Nogood Recording from Restarts", C. Lecoutre et al.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 20/06/13
 */
public class UnitNogood implements INogood {

    final IntVar var;
    final int value;


    public UnitNogood(IntVar var, int value) {
        this.value = value;
        this.var = var;
    }

    public boolean propagate(PropNogoodStore pngs) throws ContradictionException {
        return var.removeValue(value, pngs);
    }

    @Override
    public boolean isUnit() {
        return true;
    }

    public ESat isEntailed() {
        if (var.contains(value)) {
            if (var.isInstantiated()) {
                return ESat.FALSE;
            } else {
                return ESat.UNDEFINED;
            }
        }
        return ESat.TRUE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(var.getName()).append("==").append(value).append(',');
        return sb.toString();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public IntVar getVar(int i) {
        if (i == 0) return var;
        return null;
    }

    @Override
    public int getVal(int i) {
        if (i == 0) return value;
        return Integer.MAX_VALUE;
    }
}
