package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefItem{
	private String pos;
	private String text;
	private List<TrItem> tr;
	private String ts;
}