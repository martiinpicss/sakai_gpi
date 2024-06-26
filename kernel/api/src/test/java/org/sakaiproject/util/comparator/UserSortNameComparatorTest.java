/**
 * Copyright (c) 2003-2020 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.util.comparator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.sakaiproject.user.api.User;

public class UserSortNameComparatorTest {

    @Test
    public void nullSafeCompare() {
        UserSortNameComparator nullsHighComparator = new UserSortNameComparator();
        UserSortNameComparator nullsLowComparator = new UserSortNameComparator(true);
        User userA = Mockito.mock(User.class);
        when(userA.getSortName()).thenReturn("One, One");
        User userB = Mockito.mock(User.class);
        when(userB.getSortName()).thenReturn("Two, Two");
        User userC = Mockito.mock(User.class);
        when(userC.getSortName()).thenReturn(null);

        // null high
        assertEquals(0, nullsHighComparator.compare(null, null));
        assertEquals(-1, nullsHighComparator.compare(userA, null));
        assertEquals(1, nullsHighComparator.compare(null, userA));
        assertEquals(1, nullsHighComparator.compare(null, userC));
        assertEquals(-1, nullsHighComparator.compare(userC, null));
        assertEquals(-1, nullsHighComparator.compare(userA, userC));
        assertEquals(1, nullsHighComparator.compare(userC, userA));

        // null low
        assertEquals(0, nullsLowComparator.compare(null, null));
        assertEquals(1, nullsLowComparator.compare(userA, null));
        assertEquals(-1, nullsLowComparator.compare(null, userA));
        assertEquals(1, nullsLowComparator.compare(userC, null));
        assertEquals(1, nullsLowComparator.compare(userA, userC));
        assertEquals(-1, nullsLowComparator.compare(userC, userA));

        // non null
        assertEquals(-1, nullsHighComparator.compare(userA, userB));
    }

    @Test
    public void underscoreCompare() {
        UserSortNameComparator comparator = new UserSortNameComparator();
        User userA = Mockito.mock(User.class);
        when(userA.getSortName()).thenReturn("One, One");
        User userB = Mockito.mock(User.class);
        when(userB.getSortName()).thenReturn("_Two, Two");

        assertEquals(1, comparator.compare(userA, userB));
        assertEquals(-1, comparator.compare(userB, userA));
        assertEquals(0, comparator.compare(userB, userB));
    }

    @Test
    public void namesWithSpacesCompare() {
        UserSortNameComparator comparator = new UserSortNameComparator();
        User userA = Mockito.mock(User.class);
        when(userA.getSortName()).thenReturn("Dekfort, Apple");
        User userB = Mockito.mock(User.class);
        when(userB.getSortName()).thenReturn("Del Fintino, Pear");
        User userC = Mockito.mock(User.class);
        when(userC.getSortName()).thenReturn("Dekford", "Orange");
        User userD = Mockito.mock(User.class);
        when(userD.getSortName()).thenReturn("De'Leon", "Cactus");
        User userE = Mockito.mock(User.class);
        when(userE.getSortName()).thenReturn("Deleverde", "Mango");

        assertEquals(-1, comparator.compare(userA, userB));
        assertEquals(1, comparator.compare(userB, userA));
        assertEquals(0, comparator.compare(userB, userB));
        assertEquals(-1, comparator.compare(userD, userC));
        assertEquals(-1, comparator.compare(userD, userE));
    }

    @Test
    public void fullnamesWithSpacesCompare() {
        // Given two students, whose lastnames are "Martinez Torcal" and "Martin Troncoso", the logical alphabetical order 
        // is first "Martin Troncoso" and after "Martinez Torcal", just because the blank space is counted as a character.
        UserSortNameComparator comparator = new UserSortNameComparator();
        User userA = Mockito.mock(User.class);
        when(userA.getSortName()).thenReturn("Martinez Torcal, Apple");
        User userB = Mockito.mock(User.class);
        when(userB.getSortName()).thenReturn("Martin Troncoso, X");
        assertEquals(1, comparator.compare(userA, userB));

        User userC = Mockito.mock(User.class);
        when(userC.getSortName()).thenReturn("Will, Xavier Raoul Beñat Edorta");
        User userD = Mockito.mock(User.class);
        when(userD.getSortName()).thenReturn("Williams, Abigail");
        assertEquals(-1, comparator.compare(userC, userD));

        User userE = Mockito.mock(User.class);
        when(userE.getSortName()).thenReturn("Zong, Chen Guanting");
        User userF = Mockito.mock(User.class);
        when(userF.getSortName()).thenReturn("Zong Bi, A");
        assertEquals(-1, comparator.compare(userE, userF));

        User userG = Mockito.mock(User.class);
        when(userG.getSortName()).thenReturn("Fredriksson, Maximilian Isak");
        User userH = Mockito.mock(User.class);
        when(userH.getSortName()).thenReturn("Fredriksson, Max Teo");
        assertEquals(1, comparator.compare(userG, userH));
    }

    // We expect the eid to be used as a secondary comparison when names are identical
    @Test
    public void identicalNameCompare() {
        UserSortNameComparator comparator = new UserSortNameComparator();
        User userA = Mockito.mock(User.class);
        when(userA.getSortName()).thenReturn("Smith, John");
        when(userA.getDisplayId()).thenReturn(("jsmith"));
        User userB = Mockito.mock(User.class);
        when(userB.getSortName()).thenReturn("Smith, John");
        when(userB.getDisplayId()).thenReturn(("smithj"));
        User userC = Mockito.mock(User.class);
        when(userC.getSortName()).thenReturn("Smith, John");
        when(userC.getDisplayId()).thenReturn(("smithj1"));
        assertEquals(-1, comparator.compare(userA, userB));
        assertEquals(-1, comparator.compare(userA, userC));
        assertEquals(-1, comparator.compare(userB, userC));
    }

    @Test
    public void displayNameCompare() {
        UserSortNameComparator comparator = new UserSortNameComparator(true, true);
        User userA = Mockito.mock(User.class);
        when(userA.getDisplayName()).thenReturn("Alfons Albert Albertson");
        User userB = Mockito.mock(User.class);
        when(userB.getDisplayName()).thenReturn("A. Al Albert");
        User userC = Mockito.mock(User.class);
        when(userC.getDisplayName()).thenReturn("Albert Al Alberts");
        assertEquals(1, comparator.compare(userA, userB));
        assertEquals(1, comparator.compare(userA, userC));
        assertEquals(-1, comparator.compare(userB, userC));
    }
}
