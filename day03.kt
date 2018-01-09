package advent3

/*
	37 36 35 34 33 32 31
	38 17 16 15 14 13 30	
	39 18  5  4  3 12 29
	40 19  6  1  2 11 28
	41 20  7  8  9 10 27
	42 21 22 23 24 25 26
	43 44 45 46 47 48 49
*/

import kotlin.math.*

fun side(input: Int) : Int {
	var theSide = ceil(sqrt(input.toDouble())).toInt()
	if (theSide % 2 == 0) {
		theSide++
	}

	return theSide
}

fun part1(input: Int) : Int {
	val side: Int = side(input)
	var topCorner = side * side
	val corners = listOf(
		topCorner, 
		topCorner - side + 1, 
		topCorner - 2 * side + 2,
		topCorner - 3 * side + 3,
		topCorner - 4 * side + 4)

	val minDistance = corners.map {(it - input).absoluteValue}.min() ?: 0

	return side - 1 - minDistance
}

data class Item(var row: Int, var col: Int, val value: Int)

fun coordsForSize(size: Int): List<Pair<Int,Int>> {
	val result = mutableListOf<Pair<Int,Int>>()

	// right side
	for(i in (size-2) downTo 0) {
		result.add(i to size-1)
	}

	// top side
	for(i in (size-2) downTo 0) {
		result.add(0 to i)
	}

	// left side
	for(i in 1..(size-1)) {
		result.add(i to 0)
	}

	// bottom side
	for (i in 1..(size-1)) {
		result.add(size-1 to i)
	}

	return result
}

fun shiftInside(side:Int, items: MutableList<Item>) : MutableList<Item> {
	var result = mutableListOf<Item>()
	result = items.filterTo(result, {
			it.row == 0 || it.row == side -1 || it.col == 0 || it.col == side -1
		})
	
	result.forEach {
			it.row++
			it.col++
		}

	return result
}

fun valueFor(items: List<Item>, coord: Pair<Int,Int>): Item {

	val neighbour: List<Pair<Int,Int>> = listOf(
		coord.first -1 to coord.second -1,
		coord.first -1 to coord.second,
		coord.first -1 to coord.second +1,
		coord.first +1 to coord.second -1,
		coord.first +1 to coord.second,
		coord.first +1 to coord.second +1,
		coord.first to coord.second -1,
		coord.first to coord.second +1)

	val sum = items.filter { x ->
		neighbour.any { y -> y.first == x.row && y.second == x.col }
	}
	.sumBy { it.value }

	return Item(coord.first, coord.second, sum)
}

fun part2() {
	var side = 3
	var items = mutableListOf<Item>(Item(1,1,1))

	while( side < 10 ) {

		val coords = coordsForSize(side)
		for (coord in coords) {
			items.add(valueFor(items, coord))
		}

		items = shiftInside(side, items)
		side += 2
	}

	println(items.toString())
}

fun main(args: Array<String>) {
	//println(part1(312051))
	part2()
}