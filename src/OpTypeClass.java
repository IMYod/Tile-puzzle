//A class that envelops OpType
//Some functionalities

public class OpTypeClass {
	OpType type;
	
	public OpType getType() {
		return type;
	}
	
	//How many types there are
	public int getTypeSize() {
		return OpType.values().length;
	}
	
	public int ordinal() {
		return type.ordinal();
	}

	//Constructors
	public OpTypeClass( ) {
		type = OpType.L;
	}
	
	//By ordinal number
	public OpTypeClass(int index) {
		type = OpType.values()[index];
	}
	
	public OpTypeClass(OpType _type) {
		type = _type;
	}

	public OpTypeClass(OpTypeClass other) {
		type = other.type;
	}
	
	//Two types of operators, horizontal and vertical
	public boolean isHorizontal() {
		return (type == OpType.L || type == OpType.R);
	}
	
	public boolean isVertical() {
		return (type == OpType.U || type == OpType.D);
	}
	
	//Object with opposite direction
	public OpTypeClass reverse() {
		OpType reverseType = OpType.D; //default
		switch (type) {
		case D:
			reverseType = OpType.U;
			break;
		case L:
			reverseType = OpType.R;
			break;
		case R:
			reverseType = OpType.L;
			break;
		case U:
			reverseType = OpType.D;
			break;
		}
		return new OpTypeClass(reverseType);
	}
	
	public String toString() {
		return type.name();
	}
	
	public boolean equals(OpTypeClass other) {
		return type==other.type;
	}

}
