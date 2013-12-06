package com.example.streamingaudioplayer;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duron.streamingaudioplayer.arrayadapters.SongAdapter;
import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.clients.SongClient;
import com.duron.streamingaudioplayer.models.Song;

public class SongFragment extends Fragment {
	private ArrayList<Song> songs = null;
	private String album;
	private ListView songListView;
	private SongAdapter songAdapter;
	private ControllerActivity parent;
	private final SongClient songClient = new SongClient(new ClientCommand(){

		@SuppressWarnings("unchecked")
		@Override
		public void execute(Object param) {
			if(param instanceof ArrayList<?>){
				songs = (ArrayList<Song>)param;
				songAdapter = new SongAdapter(getActivity(), R.layout.cell_song, songs);
				songListView.setAdapter(songAdapter);
			}
			
		}}, new ClientCommand(){

		@Override
		public void execute(Object param) {
			//Failure
			
		}});
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Song song = songs.get(arg2);
			parent.changePlayer(song.trackName, song.trackNumber, song.trackUrl);
			
		}};
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_song, container, false);
		songListView = (ListView)view.findViewById(R.id.song_listview);
		songListView.setOnItemClickListener(itemClickListener);
		parent = (ControllerActivity)getActivity();
		
		return view;
	}
	
	public void setAlbum(String alBum){
		album = alBum;
	}
	
	
	@Override
	public void onStart(){
		super.onStart();
		songClient.getSong(album, getResources().getString(R.string.get_songs));
	}
	


	

}