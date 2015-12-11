/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.db.display;

import org.assertj.core.api.Assertions;
import org.assertj.db.common.AbstractTest;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.db.display.Displaying.display;

/**
 * Test the display of rows.
 *
 * @author Régis Pouiller
 */
public class DisplayRow_Test extends AbstractTest {

  /**
   * This method tests the {@code display} display method.
   */
  @Test
  public void test_display_for_table() throws Exception {
    Table table = new Table(source, "actor");

    ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
    display(table).row().display(new PrintStream(byteArrayOutputStream0))
                  .row(1).display(new PrintStream(byteArrayOutputStream1))
                  .row().display(new PrintStream(byteArrayOutputStream2));
    Assertions.assertThat(byteArrayOutputStream0.toString()).isEqualTo(String.format("[Row at index 0 of actor table]%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                    + "|         | *         |           |           |            |                                      |%n"
                                                                                    + "| PRIMARY | ID        | NAME      | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                    + "| KEY     | (NUMBER)  | (TEXT)    | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                    + "|         | Index : 0 | Index : 1 | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                    + "| 1       | 1         | Weaver    | Sigourney | 1949-10-08 | 30b443ae-c0c9-4790-9bec-ce1380808435 |%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"));
    Assertions.assertThat(byteArrayOutputStream1.toString()).isEqualTo(String.format("[Row at index 1 of actor table]%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         | *         |           |           |            |                                      |%n"
                                                                                     + "| PRIMARY | ID        | NAME      | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                     + "| KEY     | (NUMBER)  | (TEXT)    | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                     + "|         | Index : 0 | Index : 1 | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                     + "| 2       | 2         | Phoenix   | Joaquim   | 1974-10-28 | 16319617-ae95-4087-9264-d3d21bf611b6 |%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"));
    Assertions.assertThat(byteArrayOutputStream2.toString()).isEqualTo(String.format("[Row at index 2 of actor table]%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         | *         |             |           |            |                                      |%n"
                                                                                     + "| PRIMARY | ID        | NAME        | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                     + "| KEY     | (NUMBER)  | (TEXT)      | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                     + "|         | Index : 0 | Index : 1   | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"
                                                                                     + "| 3       | 3         | Worthington | Sam       | 1976-08-02 | d735221b-5de5-4112-aa1e-49090cb75ada |%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"));
  }

  /**
   * This method tests the {@code display} display method.
   */
  @Test
  public void test_display_for_request() throws Exception {
    Request request = new Request(source, "select * from actor");

    ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
    display(request).row().display(new PrintStream(byteArrayOutputStream0))
                    .row(1).display(new PrintStream(byteArrayOutputStream1))
                    .row().display(new PrintStream(byteArrayOutputStream2));
    Assertions.assertThat(byteArrayOutputStream0.toString()).isEqualTo(String.format("[Row at index 0 of 'select * from actor' request]%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                    + "|         |           |           |           |            |                                      |%n"
                                                                                    + "| PRIMARY | ID        | NAME      | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                    + "| KEY     | (NUMBER)  | (TEXT)    | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                    + "|         | Index : 0 | Index : 1 | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                    + "|         | 1         | Weaver    | Sigourney | 1949-10-08 | 30b443ae-c0c9-4790-9bec-ce1380808435 |%n"
                                                                                    + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"));
    Assertions.assertThat(byteArrayOutputStream1.toString()).isEqualTo(String.format("[Row at index 1 of 'select * from actor' request]%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         |           |           |           |            |                                      |%n"
                                                                                     + "| PRIMARY | ID        | NAME      | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                     + "| KEY     | (NUMBER)  | (TEXT)    | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                     + "|         | Index : 0 | Index : 1 | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         | 2         | Phoenix   | Joaquim   | 1974-10-28 | 16319617-ae95-4087-9264-d3d21bf611b6 |%n"
                                                                                     + "|---------|-----------|-----------|-----------|------------|--------------------------------------|%n"));
    Assertions.assertThat(byteArrayOutputStream2.toString()).isEqualTo(String.format("[Row at index 2 of 'select * from actor' request]%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         |           |             |           |            |                                      |%n"
                                                                                     + "| PRIMARY | ID        | NAME        | FIRSTNAME | BIRTH      | ACTOR_IMDB                           |%n"
                                                                                     + "| KEY     | (NUMBER)  | (TEXT)      | (TEXT)    | (DATE)     | (UUID)                               |%n"
                                                                                     + "|         | Index : 0 | Index : 1   | Index : 2 | Index : 3  | Index : 4                            |%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"
                                                                                     + "|         | 3         | Worthington | Sam       | 1976-08-02 | d735221b-5de5-4112-aa1e-49090cb75ada |%n"
                                                                                     + "|---------|-----------|-------------|-----------|------------|--------------------------------------|%n"));
  }
}
