package cabinetcep.com.meditation

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import cabinetcep.com.meditation.databinding.FragmentPreferencesBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MeditationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MeditationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PreferencesFragment : PreferencesStorage(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentPreferencesBinding
    private var ring : MediaPlayer? = null

    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    private fun initSpinnerRing() {

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.sounds_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.ringSpinner.adapter = adapter
        }
        binding.ringSpinner.onItemSelectedListener = this
    }

    private fun initSpinnerDuration() {

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.duration_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.timeSpinner.adapter = adapter
        }
        binding.timeSpinner.onItemSelectedListener = this
    }

    private fun initRingTestButton() {
        binding.ringButton.setOnClickListener {
            Timber.d("start playing...")
            playRing()
        }
    }

    private fun initMediaPlayer() {
        ring = MediaPlayer.create(this.context, R.raw.chime)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        initSpinnerRing()
        initSpinnerDuration()
        initMediaPlayer()
        initRingTestButton()
        initSharedPreferences()
        return binding.root
    }

    private fun initSharedPreferences() {
        val localUserPreferences = getUserPreferences()
        binding.ringSpinner.setSelection(resources.getStringArray(R.array.sounds_array).indexOf(localUserPreferences?.ringName))
        binding.timeSpinner.setSelection(resources.getStringArray(R.array.duration_array).indexOf(localUserPreferences?.duration))
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        when(parent.id) {
            R.id.ring_spinner-> {
                val ringName = parent.getItemAtPosition(pos).toString()
                binding.ringName = RingName(ringName)
                storeRing(ringName)
                Timber.d(binding.ringName.toString())
            }
            R.id.time_spinner -> {
                val timeName = parent.getItemAtPosition(pos).toString()
                binding.durationName = DurationName(timeName)
                storeDuration(timeName)
                Timber.d(binding.durationName.toString())
            }
            else -> Timber.d("no spinner view found")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun playRing() {
        if (this.ring != null && this.ring!!.isPlaying) {
            return
        }
        val resMp3 = when (binding.ringName?.name) {
            "chime" -> R.raw.chime
            "graceful" -> R.raw.graceful
            else -> R.raw.oringzw465
        }
        Timber.d(binding.ringName.toString())
        this.ring?.release()
        this.ring = null
        // create a player ready to play the mp3 file from the res/raw folder
        this.ring = MediaPlayer.create(this.context, resMp3)
        //this.ring.setVolume(100.0f, 100.0f)
        this.ring?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        ring?.release()
    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MeditationFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PreferencesFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}
