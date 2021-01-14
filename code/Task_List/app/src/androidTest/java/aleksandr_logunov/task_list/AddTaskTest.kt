package aleksandr_logunov.task_list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddTaskTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addTaskTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.plus_fab), withContentDescription("Add task"),
                childAtPosition(
                    allOf(
                        withId(R.id.app_bar_main),
                        childAtPosition(
                            withId(R.id.drawer_layout),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val id = Date().time

        val taskTitleET = onView(withId(R.id.et_task_title))
        val titleText = "Title of the task $id"
        taskTitleET.perform(scrollTo(), replaceText(titleText), closeSoftKeyboard())

        val taskDescriptionET = onView(allOf(withId(R.id.et_task_description)))
        val descriptionText = "Task description $id"
        taskDescriptionET.perform(scrollTo(), replaceText(descriptionText), closeSoftKeyboard())

        val saveButton = onView(
            allOf(
                withId(R.id.task_save), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        saveButton.perform(click())

        val tasksRV = onView(withId(R.id.rv_task_list))

        tasksRV.perform(ScrollRVToBottomAction())

        val titleTV = onView(
            allOf(
                withId(R.id.tv_task_title),
                withText(titleText),
                withParent(withParent(withId(R.id.cv_task))),
            )
        )
        titleTV.check(matches(withText(titleText)))

        val descriptionTV = onView(
            allOf(
                withId(R.id.tv_task_description),
                withText(descriptionText),
                withParent(withParent(withId(R.id.cv_task))),
            )
        )
        descriptionTV.check(matches(withText(descriptionText)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

// from https://stackoverflow.com/questions/42915073/espresso-recyclerview-scroll-to-end
class ScrollRVToBottomAction : ViewAction {
    override fun getDescription(): String {
        return "scroll RecyclerView to bottom"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        val position = itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}
