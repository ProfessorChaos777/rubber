package com.example.rubberbridge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rubberbridge.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetPlayers : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //var text_length = binding.editTextPlayer1.length()

            if( binding.editTextPlayer1.length() == 0 ||
                binding.editTextPlayer2.length() == 0 ||
                binding.editTextPlayer3.length() == 0 ||
                binding.editTextPlayer4.length() == 0 ) {

                binding.errorText.setText(getString(R.string.not_enough_players))
            }
            else {
                findNavController().navigate(R.id.action_SetPlayers_to_Table)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}