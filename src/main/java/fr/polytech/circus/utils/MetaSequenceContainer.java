package fr.polytech.circus.utils;

import fr.polytech.circus.model.MetaSequence;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MetaSequenceContainer implements Serializable
	{
	private final List< MetaSequence > metaSequences = new ArrayList<> ();

	public MetaSequenceContainer ()
		{
		Instant start = Instant.parse ( "2021-11-11T12:00:00.00Z" );
		Instant end   = Instant.parse ( "2021-11-11T15:00:00.00Z" );

		metaSequences.add ( new MetaSequence ( "Metaséquence 1", Duration.between ( start, end ) ) );
		metaSequences.add ( new MetaSequence ( "Metaséquence 2", Duration.between ( start, end ) ) );
		metaSequences.add ( new MetaSequence ( "Metaséquence 3", Duration.between ( start, end ) ) );
		}

	public List< MetaSequence > getMetaSequences ()
		{
		return metaSequences;
		}
	}
