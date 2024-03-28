package com.example.unihealth

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.MealAdapter
import com.example.unihealth.classes.RecipeMealPlans

class MealPlans : AppCompatActivity() {

    private lateinit var mealAdapter: MealAdapter

    val mealList = listOf(
        RecipeMealPlans(
            "Stir-Fried Tofu with Vegetables",
            R.drawable.stir_fried_tofu_with_vegetables,
            listOf(
                "Firm tofu, cubed",
                "Broccoli florets",
                "Bell peppers(red, green, yellow)",
                "Snow peas",
                "Carrots, julienned",
                "Garlic, minced",
                "Soy sauce",
                "Sesame oil",
                "Brown rice(optional, for serving)"
            ),
            300,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Turkey and Vegetable Quinoa Bowl",
            R.drawable.turkey_and_vegetable_quinoa_bowl,
            listOf(
                "Lean ground turkey",
                "Quinoa, cooked",
                "Zucchini, diced",
                "Yellow squash, diced",
                "Red bell pepper, diced",
                "Onion, diced",
                "Garlic, minced",
                "Olive oil",
                "Salt and pepper to taste",
                "Fresh parsley for garnish"
            ),
            400,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Black Bean Burger with avocado Cream",
            R.drawable.black_bean_burger_with_avocado_cream,
            listOf(
                "Black Beans",
                "Zucchini",
                "Bell Pepper",
                "Flaxseed",
                "Chili Powder",
                "Smoked Paprika",
                "Avocado",
                "Lettuce Wrap"
            ),
            400,
            listOf("Vegetarian", "Low Carbs")
        ),
        RecipeMealPlans(
            "Grilled Lemon Herb Chicken with Quinoa Salad",
            R.drawable.grilled_lemon_herb_chicken_with_quinoa_salad,
            listOf(
                "Boneless, skinless chicken breasts",
                "Lemon",
                "Olive oil",
                "Garlic, minced",
                "Oregano",
                "Dried thyme",
                "Salt and pepper",
                "Quinoa",
                "Cherry tomatoes",
                "Cucumber",
                "Red onion",
                "Fresh parsley",
                "Olive oil"
            ),
            450,
            listOf("Nut Free")
        ),
        RecipeMealPlans(
            "Spaghetti with Tomato Basil Sauce",
            R.drawable.spaghetti_with_tomato_basil_sauce,
            listOf(
                "Spaghetti pasta",
                "Olive oil",
                "Garlic",
                "Crushed tomatoes",
                "Dried basil",
                "Salt and pepper",
                "Fresh basil leaves"
            ),
            390,
            listOf("Nut Free")
        ),
        RecipeMealPlans(
            "Salmon with Roasted Vegetables",
            R.drawable.salmon_with_roasted_vegetables,
            listOf(
                "Salmon fillets",
                "Asparagus spears",
                "Brussels sprouts, halved",
                "Cherry tomatoes",
                "Red onion, sliced",
                "Olive oil",
                "Lemon juice",
                "Garlic powder",
                "Paprika",
                "Salt and pepper to taste"
            ),
            380,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Hemp Seed and Chia Pudding(with Berries)",
            R.drawable.hemp_seed_and_chia_pudding_with_berries_,
            listOf(
                "Hemp Seeds",
                "Chia Seeds",
                "Almond Milk",
                "Coconut Milk",
                "Berries(fresh or frozen)"
            ),
            300,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Vegan Lentil Salad",
            R.drawable.vegan_lentil_salad,
            listOf(
                "Cooked green lentils",
                "Diced cucumber",
                "Cherry tomatoes, halved",
                "Chopped red onion",
                "Chopped fresh parsley",
                "Lemon juice",
                "Extra virgin olive oil",
                "Dijon mustard",
                "Salt and pepper"
            ),
            270,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Lentil Vegetable Soup",
            R.drawable.lentil_vegetable_soup,
            listOf(
                "Dried green lentils",
                "Vegetable broth",
                "Onion",
                "Carrots",
                "Celery",
                "Garlic",
                "Dried thyme",
                "Dried oregano",
                "Bay leaf",
                "Salt and pepper",
                "Spinach",
                "Olive oil"
            ),
            280,
            listOf("Nut Free")
        ),
        RecipeMealPlans(
            "Spicy Tofu Scramble",
            R.drawable.spicy_tofu_scramble,
            listOf(
                "Saute Crumbled tofu",
                "Chopped Onions",
                "Bell peppers",
                "Chili Powder",
                "Vegetable Broth"
            ),
            300,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Cauliflower Curry",
            R.drawable.cauliflower_curry,
            listOf(

                "1 tablespoon extra-virgin olive oil",
                "1 medium yellow onion, diced",
                "4 cloves garlic, minced",
                "1 tablespoon minced fresh ginger",
                "3 tablespoons yellow curry powder",
                "2 teaspoons cinnamon",
                "2 teaspoons cumin",
                "2 teaspoons sea salt",
                "⅛ teaspoon cayenne pepper",
                "1 (14-ounce) can diced tomatoes",
                "3 (14-ounce) cans coconut milk or unsweetened coconut cream",
                "1 large head of cauliflower, cut into florets",
                "Cooked rice, for serving (optional)",
                "Minced cilantro, for serving (optional)",
                "Warmed naan, for serving (optional)"
            ),
            280,
            listOf("Vegan", "Vegetarian")
        ),
        RecipeMealPlans(
            "Christmas Salad",
            R.drawable.christmas_salad,
            listOf(
                "½ cup extra-virgin olive oil",
                "1 tablespoon white wine vinegar (see Note)",
                "1 teaspoon grated clementine zest",
                "⅓ cup fresh clementine juice (from about 6 clementines)",
                "2 teaspoons honey",
                "1 teaspoon Dijon mustard",
                "2 tablespoons minced shallots",
                "¾ teaspoon sea salt",
                "¼ teaspoon freshly cracked black pepper",
                "5 ounces baby spinach (about 4 cups)",
                "5 ounces baby arugula (about 3 cups)",
                "4 ounces crumbled feta or goat cheese",
                "6 clementines, segmented",
                "½ cup pomegranate seeds or craisins",
                "⅓ cup sliced almonds",
                "¼ cup minced chives (optional)"
            ),
            283,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Grilled Chicken Salad",
            R.drawable.grilled_chicken_salad,
            listOf(
                "Grilled chicken breast",
                "Mixed salad greens (lettuce, spinach, arugula)",
                "Cherry tomatoes",
                "Cucumber",
                "Red onion",
                "Balsamic vinaigrette dressing"
            ),
            350,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Homemade Fried Rice",
            R.drawable.homemade_fried_rice,
            listOf(
                "4 cups cooked white rice, preferably day-old",
                "3 large eggs, beaten",
                "¼ teaspoon sea salt, plus more to taste",
                "2½ teaspoons toasted sesame oil",
                "⅓ cup coconut aminos",
                "3 tablespoons vegetable oil",
                "2 medium carrots, finely diced (about 1 cup)",
                "1 small yellow onion, finely chopped (about 1 cup)",
                "3 cloves garlic, minced",
                "¾ cup frozen peas, thawed",
                "4 green onions, chopped",
                "2 tablespoons soy sauce"
            ),
            350,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Dairy Free Mac and Cheese",
            R.drawable.dairy_free_mac_and_cheese,
            listOf(
                "1½ pounds russet potatoes, peeled and cut into 2-inch pieces",
                "16 ounces elbow pasta",
                "2 cups chicken or veggie stock",
                "2 cups vegan cheese shreds (we like Violife Mozzarella Shreds)",
                "½ cup nutritional yeast",
                "1 teaspoon garlic powder",
                "½ teaspoon onion powder",
                "1½ teaspoons kosher salt",
                "¼ teaspoon turmeric",
                "¼ teaspoon paprika",
                "1½ teaspoons fresh lemon juice (from 1 lemon)",
                "½ cup + 2 tablespoons vegan butter, melted",
                "¾ cup panko bread crumbs"
            ),
            506,
            listOf("Vegetarian", "Dairy Free")
        ),
        RecipeMealPlans(
            "Vegetable Stir Fry",
            R.drawable.vegetable_stir_fry,
            listOf(
                "1 tablespoon vegetable oil",
                "1 cup sliced crimini mushrooms",
                "2 cups broccoli florets",
                "1 15-ounce can baby corn, drained (optional)",
                "1 red bell pepper, thinly sliced",
                "4 ounces snap peas",
                "1 cup sliced zucchini",
                "1 cup peeled and thinly sliced carrot rounds",
                "½ cup low-sodium soy sauce or tamari",
                "1 tablespoon toasted sesame oil",
                "4 cloves garlic, minced",
                "2 teaspoons freshly grated ginger",
                "¼ cup brown sugar or honey",
                "Pinch red pepper flakes (optional)",
                "2 tablespoons cornstarch or arrowroot starch",
                "¼ cup thinly sliced green onions"
            ),
            146,
            listOf("Vegetarian", "Vegan")
        ),
        RecipeMealPlans(
            "Grated beet Salad",
            R.drawable.grated_beet_salad,
            listOf(


                "2 tablespoons extra-virgin olive oil",
                "2 tablespoons sherry vinegar",
                "1 small shallot, minced",
                "1 teaspoon Dijon mustard",
                "1 teaspoon honey",
                "½ teaspoon grated lemon zest",
                "½ teaspoon sea salt",
                "¼ teaspoon freshly cracked black pepper",
                "1 pound red beets, peeled and grated",
                "1 large carrot, peeled and grated",
                "¼ cup chopped fresh flat leaf parsley",
                "¼ cup chopped fresh mint"
            ),
            100,
            listOf("Vegetarian", "Vegan")
        ),
        RecipeMealPlans(
            "Pesto Spaghetti",
            R.drawable.pesto_spaghetti,
            listOf(

                "12 ounces spaghetti",
                "1 cup basil pesto, store-bought or homemade",
                "2 cups arugula",
                "Cherry tomatoes, halved, for serving",
                "Fresh basil leaves, for serving (optional)",
                "Parmesan cheese, for serving (optional)"
            ),
            350,
            listOf("Vegetarian")
        )
    )

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal_plans)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var filteredMeals: List<RecipeMealPlans> = mealList
        val recyclerView = findViewById<RecyclerView>(R.id.mealRecyclerView)
        val mealAdapter = MealAdapter(mealList.toMutableList())
        recyclerView.adapter = mealAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val vegetarianCheckbox = findViewById<CheckBox>(R.id.vegetarianCheckbox)
        val dairyFreeCheckbox = findViewById<CheckBox>(R.id.dairyFreeCheckbox)
        val lowCarbsCheckbox = findViewById<CheckBox>(R.id.lowCarbsCheckbox)
        val nutFreeCheckbox = findViewById<CheckBox>(R.id.nutFreeCheckbox)
        val veganCheckbox = findViewById<CheckBox>(R.id.veganCheckbox)
        val emptyFind = findViewById<TextView>(R.id.emptyFind)


        val checkboxes = listOf(
            vegetarianCheckbox,
            dairyFreeCheckbox,
            lowCarbsCheckbox,
            nutFreeCheckbox,
            veganCheckbox
        )

        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val selectedCategories = checkboxes
                    .filter { it.isChecked }
                    .map { it.text.toString() }
                val filteredMeals = if (selectedCategories.isNotEmpty()) {
                    mealList.filter { meal ->
                        meal.categories.any { it in selectedCategories }
                    }
                } else {
                    mealList
                }
                mealAdapter.updateList(filteredMeals)
            }

        }
    }
}