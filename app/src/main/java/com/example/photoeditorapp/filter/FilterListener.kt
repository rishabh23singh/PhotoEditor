package com.example.photoeditorapp.filter

import ja.burhanrashid52.photoeditor.PhotoFilter

interface FilterListener {
    fun onFilterSelected(photoFilter: PhotoFilter)
}