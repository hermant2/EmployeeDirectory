package com.treyherman.employeedirectory.scenes.maindirectory.diff

import com.treyherman.employeedirectory.mocks.ui.mockUIEmployee
import org.junit.Assert.*
import org.junit.Test

class EmployeeDiffCallbackTest {
    val oldItems = listOf(
        mockUIEmployee(uuid = "uuid1", phoneNumber = "2223334455"),
        mockUIEmployee(uuid = "uuid2"),
        mockUIEmployee("uuid3")
    )

    val newItems = listOf(
        mockUIEmployee(uuid = "uuid1", phoneNumber = null),
        mockUIEmployee(uuid = "uuid2")
    )

    val diffCallback = EmployeeDiffCallback(oldItems, newItems)

    @Test
    fun areItemsTheSame_true() {
        assertTrue(diffCallback.areItemsTheSame(0, 0))
    }

    @Test
    fun areItemsTheSame_false() {
        assertFalse(diffCallback.areItemsTheSame(0, 1))
    }

    @Test
    fun areContentsTheSame_true() {
        assertTrue(diffCallback.areContentsTheSame(1, 1))
    }

    @Test
    fun areContentsTheSame_false() {
        assertFalse(diffCallback.areContentsTheSame(0, 0))
    }

    @Test
    fun oldListSize_result() {
        assertEquals(diffCallback.oldListSize, oldItems.size)
    }

    @Test
    fun newListSize_result() {
        assertEquals(diffCallback.newListSize, newItems.size)
    }
}