package com.github.vkuzel.orm_frameworks_demo.reladomo.model;

import com.gs.fw.finder.Operation;

import java.util.*;

/********************************************************************************
 * File        : $Source:  $
 * Version     : $Revision:  $
 * Date        : $Date:  $
 * Modified by : $Author:  $
 *******************************************************************************
 */
public class PlaneList extends PlaneListAbstract {
    public PlaneList() {
        super();
    }

    public PlaneList(int initialSize) {
        super(initialSize);
    }

    public PlaneList(Collection c) {
        super(c);
    }

    public PlaneList(Operation operation) {
        super(operation);
    }
}
