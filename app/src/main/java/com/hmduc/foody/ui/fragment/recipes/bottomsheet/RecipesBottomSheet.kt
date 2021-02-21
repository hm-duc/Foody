package com.hmduc.foody.ui.fragment.recipes.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.hmduc.foody.R
import com.hmduc.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.hmduc.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.hmduc.foody.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.util.*

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealType = DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietType = DEFAULT_DIET_TYPE
    private var dietTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDiet.asLiveData().observe(viewLifecycleOwner) { value ->
            updateChip(value.selectedMealTypeId, view.chipGroup_mealType)
            updateChip(value.selectedDietTypeId, view.chipGroup_dietType)
        }

        view.chipGroup_mealType.setOnCheckedChangeListener{ group, selectedId ->
            val chip = group.findViewById<Chip>(selectedId)
            mealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeId = selectedId
        }

        view.chipGroup_dietType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            dietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeId = checkedId
        }

        view.apply_btn.setOnClickListener {
            recipesViewModel.saveMealAndDiet(mealType, mealTypeId, dietType, dietTypeId)
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return view
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            chipGroup.findViewById<Chip>(chipId).isChecked = true
        }
    }
}