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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.db.util;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Failures;
import org.assertj.db.api.AbstractAssert;
import org.assertj.db.type.Change;
import org.assertj.db.type.Changes;

import java.util.List;

import static org.assertj.db.error.ShouldHaveChangesSize.shouldHaveChangesSize;

/**
 * Utility methods related to assert on changes.
 *
 * @author Régis Pouiller
 */
public class AssertionsOnChanges {

  /**
   * To notice failures in the assertion.
   */
  private final static Failures failures = Failures.instance();

  /**
   * Private constructor.
   */
  private AssertionsOnChanges() {
    // Empty
  }

  /**
   * Verifies that the size (number) of {@link org.assertj.db.type.Changes} is equal to the number in parameter.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Info on the object to assert.
   * @param changes   The changes.
   * @param expected  The number to compare to the size.
   * @return {@code this} assertion object.
   * @throws AssertionError If the size is different to the number in parameter.
   */
  public static <A extends AbstractAssert> A hasSize(A assertion, WritableAssertionInfo info, Changes changes,
                                                     int expected) {
    List<Change> changesList = changes.getChangesList();
    int size = changesList.size();
    if (size != expected) {
      throw failures.failure(info, shouldHaveChangesSize(size, expected));
    }
    return assertion;
  }
}