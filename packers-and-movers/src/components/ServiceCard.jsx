import { useNavigate } from "react-router";

const scrollToQuoteForm = () => {
  const element = document.getElementById("quote");
  if (element) {
    element.scrollIntoView({ behavior: "smooth" });
  }
};

const ServiceCard = ({ service }) => {
  const navigate = useNavigate();

  return (
    <div
      //   onClick={() => navigate("/getquotation")}
      className="group bg-white shadow-sm hover:shadow-lg transition-all duration-300"
    >
      {/* Image */}
      <div className="relative overflow-hidden">
        <img
          src={service.image}
          alt={service.title}
          className="w-full h-[260px] object-cover transition-transform duration-500 group-hover:scale-105"
        />

        {/* Hover Overlay Button */}
        <div className="absolute inset-0 flex items-center justify-center bg-black/40 opacity-0 group-hover:opacity-100 transition-all duration-300">
          <button
            onClick={scrollToQuoteForm}
            className="px-6 py-3 bg-[#0D3B66] text-white text-sm font-semibold tracking-wide transition-all duration-300 transform translate-y-4 group-hover:translate-y-0 hover:bg-yellow-400 hover:border-2 hover:border-yellow-400 hover:text-gray-900 rounded-[25px] hover:cursor-pointer border-2"
          >
            GET QUOTATION NOW
          </button>
        </div>

        {/* Icon */}
        <div className="absolute top-4 right-4 z-10">
          <div className="w-12 h-12 flex items-center justify-center bg-white shadow transition-all duration-300 group-hover:bg-[#0D3B66]">
            <service.icon
              sx={{ fontSize: 26 }}
              className="text-black group-hover:text-white transition-all duration-300"
            />
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="p-6">
        <span className="inline-block bg-[#1171BA] text-white rounded-[15px] text-xs font-semibold px-3 py-1 mb-4">
          {service.badge}
        </span>

        <h3 className="text-lg font-bold text-gray-900 mb-3">
          {service.title}
        </h3>

        <p className="text-sm text-gray-800 leading-relaxed mb-6">
          {service.description}
        </p>

        <div
          onClick={scrollToQuoteForm}
          className="flex hover:cursor-pointer items-center gap-3 text-sm font-semibold text-gray-900"
        >
          GET QUICK QUOTE
  <span className="w-10 h-[2px] bg-yellow-400"></span>
        </div>
      </div>
    </div>
  );
};

export default ServiceCard;
