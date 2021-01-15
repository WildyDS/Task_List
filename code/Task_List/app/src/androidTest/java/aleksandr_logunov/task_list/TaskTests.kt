package aleksandr_logunov.task_list

import aleksandr_logunov.task_list.utils.ScrollRVToBottomAction
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class TaskTests {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addTaskTest() {
        val id = Date().time
        val title = "Title of the task $id"
        val description = "Task description $id"

        addTask(title, description)

        val tasksRV = onView(withId(R.id.rv_task_list))
        tasksRV.perform(ScrollRVToBottomAction())

        val titleTV = onView(
            allOf(
                withId(R.id.tv_task_title),
                withText(title),
                withParent(withParent(withId(R.id.cv_task))),
            )
        )
        titleTV.check(matches(withText(title)))

        val descriptionTV = onView(
            allOf(
                withId(R.id.tv_task_description),
                withText(description),
                withParent(withParent(withId(R.id.cv_task))),
            )
        )
        descriptionTV.check(matches(withText(description)))
    }

    @Test
    fun deleteTaskFromListTest() {
        val createdTaskMatcher = prepareForDeletion()
        val createdTask = onView(createdTaskMatcher)
        val amvTask = onView(
            allOf(
                withId(R.id.amv_task),
                withParent(withParent(createdTaskMatcher)),
            )
        )
        amvTask.perform(click())

        // TODO: use id or string resource to find delete button
        val deleteMenuItem = onView(withText("Delete"))
        deleteMenuItem.perform(click())

        try {
            createdTask.check(matches(isDisplayed()))
            throw Error("Task deletion didn't go well")
        } catch (e: NoMatchingViewException) {
            // It's okay, task deleted
        }
    }
}

private fun addTask(title: String, description: String) {
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

    val taskTitleET = onView(withId(R.id.et_task_title))
    taskTitleET.perform(scrollTo(), replaceText(title), closeSoftKeyboard())

    val taskDescriptionET = onView(allOf(withId(R.id.et_task_description)))
    taskDescriptionET.perform(scrollTo(), replaceText(description), closeSoftKeyboard())

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
}

private fun prepareForDeletion(): Matcher<View> {
    val id = Date().time
    // Avoiding bug when menu button is hidden behind plus FAB
    // https://github.com/WildyDS/Task_List/issues/4
    val title = "Title of the task LOOOOOOONG $id"
    val description = "Task description LOOOOOOONG LOOOOOOONG LOOOOOOONG vLOOOOOOONG $id"
    addTask(title, description)

    val tasksRV = onView(withId(R.id.rv_task_list))
    tasksRV.perform(ScrollRVToBottomAction())

    val createdTaskMatcher = allOf(
        withId(R.id.cv_task),
        withChild(withChild(
            allOf(
                withId(R.id.tv_task_title),
                withText(title),
                withParent(withParent(withId(R.id.cv_task))),
            )
        ))
    )
    onView(createdTaskMatcher).check(matches(isDisplayed()))
    Thread.sleep(1000)
    return createdTaskMatcher
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
