package com.richardmuttett.sleeptracker.model

import java.util.Arrays

data class SleepResponse(
    val pagination: Pagination = Pagination(),
    val sleep: Array<Sleep> = emptyArray()) {

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (other?.javaClass != javaClass) {
      return false
    }

    other as SleepResponse

    if (!Arrays.equals(sleep, other.sleep)) {
      return false
    }

    return true
  }

  override fun hashCode() = Arrays.hashCode(sleep)
}
